package com.novoda.pianohero;

import android.content.Context;
import android.os.Handler;

import jp.kshoji.driver.midi.device.MidiInputDevice;

interface MidiKeyboardDriver {

    void attachListener(NoteListener noteListener);

    void open();

    void close();

    class KeyStationMini32 extends SimpleUsbMidiDriver implements MidiKeyboardDriver {

        private final Handler mainThreadHandler;

        private NoteListener noteListener;

        KeyStationMini32(Context context) {
            super(context);
            mainThreadHandler = new Handler(context.getMainLooper());
        }

        @Override
        public void attachListener(NoteListener noteListener) {
            this.noteListener = noteListener;
        }

        @Override
        public void onMidiNoteOn(
            MidiInputDevice midiInputDevice,
            int cable,
            int channel,
            final int note,
            int velocity) {
            if (noteListener == null) {
                return;
            }
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    noteListener.onPress(new Note(note));
                }
            });
        }

        @Override
        public void onMidiNoteOff(
            MidiInputDevice midiInputDevice,
            int cable,
            int channel,
            final int note,
            int velocity) {
            if (noteListener == null) {
                return;
            }
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    noteListener.onRelease(new Note(note));
                }
            });
        }
    }
}
