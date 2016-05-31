package snapaudioObjects;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.hassan.summertraining.indielabs.snapaudio.R;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Hassan on 5/31/16.
 */
public class AudioAdapter extends ArrayAdapter implements View.OnTouchListener {

    List list = new ArrayList<>();
    AudioHolder audioHolder;

    public AudioAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Audio audio){
        super.add(audio);
        list.add(audio);
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row ;
        row = convertView;
        AudioHolder audioHolder;

        if(row == null){
           // filling the view content of a row
            LayoutInflater layoutInflater =  (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.myAudioList_item,parent,false);
            audioHolder = new AudioHolder();
            //audioHolder.audio_source = ;
            audioHolder.play_button = (ImageButton) row.findViewById(R.id.item_playButton);
            audioHolder.sender_usernameTextView = (TextView) row.findViewById(R.id.item_sender_name_TextView);
            row.setTag(audioHolder);

        }
        else{
            audioHolder = (AudioHolder) row.getTag();
        }

        //filling the content of a row with the database information
        Audio audio = (Audio) this.getItem(position);
        audioHolder.sender_usernameTextView.setText(audio.getAudio_creator_id());
        audioHolder.audio_source = audio.getAudio_directory();
        audioHolder.play_button.setOnTouchListener(this);

        return super.getView(position, convertView, parent);
    }

    @Override
    // on touch will be called to play the received audio
    public boolean onTouch(View v, MotionEvent event) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        int action = event.getAction();
        if(v.getId() == R.id.playRecordedButton){
                if (action == MotionEvent.ACTION_DOWN){
                    if(mediaPlayer != null)
                        mediaPlayer.release();
                    try {
                        mediaPlayer.setDataSource(audioHolder.audio_source);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        Toast.makeText(getContext(),"some error occur :(",Toast.LENGTH_LONG);
                    }


                }


                else if (action == MotionEvent.ACTION_UP) {
                    mediaPlayer.stop();
                    // the audio should be deleted once the user release the play button
                }

        }
        return false;
    }



    static class AudioHolder{
         ImageButton play_button;
         TextView sender_usernameTextView;
         String audio_source;

    }
}
