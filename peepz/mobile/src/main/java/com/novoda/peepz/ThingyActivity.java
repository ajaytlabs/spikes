package com.novoda.peepz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;

import com.ataulm.rv.SpacesItemDecoration;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThingyActivity extends BaseActivity {

    private static final String KEY_ROOT = "wall";

    @BindView(R.id.thingy_selfie)
    SelfieView selfieView;

    @BindView(R.id.thingy_collection)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thingy);
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
            selfieView.takePicture();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        selfieView.attach(listener);
    }

    @Override
    protected void onPause() {
        selfieView.detachListeners();
        super.onPause();
    }

    private final SelfieView.Listener listener = new SelfieView.Listener() {
        @Override
        public void onPictureTaken(byte[] data) {
            FirebaseUser user = firebaseApi().getSignedInUser();
            long currentTimeMillis = System.currentTimeMillis();

            // TODO: upload image to Firebase Storage
//            String encodedImage = new StringBuilder("data:image/webp;base64,")
//                    .append(Base64.encodeToString(data, Base64.DEFAULT))
//                    .toString();
            String urlToImageWithTokenToAccess = "";

            ApiPeep apiPeep = ApiPeep.create(
                    user.getUid(),
                    user.getDisplayName(),
                    urlToImageWithTokenToAccess,
                    currentTimeMillis,
                    currentTimeMillis
            );

//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            database.getReference(KEY_ROOT).child(user.getUid()).setValue(apiPeep);
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
        Peep user = findUserIn(peepz);
        peepz.remove(user);
        selfieView.bind(user);

        if (recyclerView.getAdapter() == null) {
            PeepAdapter peepAdapter = new PeepAdapter(peepz);
            recyclerView.setAdapter(peepAdapter);
        } else {
            ((PeepAdapter) recyclerView.getAdapter()).update(peepz);
        }
    }

    @Nullable
    private Peep findUserIn(List<Peep> peepz) {
        for (Peep peep : peepz) {
            if (peep.id().equals(firebaseApi().getSignedInUser().getUid())) {
                return peep;
            }
        }
        return null;
    }

}
