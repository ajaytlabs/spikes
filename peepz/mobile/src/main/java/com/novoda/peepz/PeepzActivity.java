package com.novoda.peepz;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ataulm.rv.SpacesItemDecoration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeepzActivity extends BaseActivity {

    private static final String KEY_ROOT = "wall";

    @BindView(R.id.peepz_collection)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peepz);
        ButterKnife.bind(this);

        int spans = getResources().getInteger(R.integer.spans);
        recyclerView.setLayoutManager(new GridLayoutManager(this, spans));
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerView.addItemDecoration(SpacesItemDecoration.newInstance(dimensionPixelSize, dimensionPixelSize, spans));
        fetchData();

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.thingy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.thingy_take_picture) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private final SelfieView.Listener listener = new SelfieView.Listener() {
        @Override
        public void onPictureTaken(byte[] data) {
            final FirebaseUser user = firebaseApi().getSignedInUser();
            final long currentTimeMillis = System.currentTimeMillis();
            StorageReference destination = FirebaseStorage.getInstance().getReference().child(KEY_ROOT + "/" + user.getUid() + ".png");

            UploadTask uploadTask = destination.putBytes(data);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult().getDownloadUrl();

                        ApiPeep apiPeep = ApiPeep.create(
                                user.getUid(),
                                user.getDisplayName(),
                                downloadUrl.toString(),
                                currentTimeMillis,
                                currentTimeMillis
                        );

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        database.getReference(KEY_ROOT).child(user.getUid()).setValue(apiPeep);
                    } else {
                        // TODO: image upload failed - retry a couple times and then give up
                    }
                }
            });
        }
    };

    private void fetchData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference wallRef = database.getReference(KEY_ROOT);
        wallRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot wall) {
                Converter converter = new Converter();
                List<Peep> peepz = new ArrayList<>((int) wall.getChildrenCount());
                for (DataSnapshot item : wall.getChildren()) {
                    Peep peep = converter.convert(item);
                    peepz.add(peep);
                }
                onNext(peepz);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO: on error?
            }
        });
    }

    private void onNext(List<Peep> peepz) {
        if (recyclerView.getAdapter() == null) {
            String signedInUserUid = firebaseApi().getSignedInUser().getUid();
            Comparator<Peep> comparator = new SignedInUserIsFirstPeepzComparator(signedInUserUid);
            PeepAdapter peepAdapter = new PeepAdapter(peepz, comparator);
            recyclerView.setAdapter(peepAdapter);
        } else {
            ((PeepAdapter) recyclerView.getAdapter()).update(peepz);
        }
    }

}
