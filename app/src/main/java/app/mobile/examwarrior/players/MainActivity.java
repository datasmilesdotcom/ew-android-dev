package app.mobile.examwarrior.players;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import app.mobile.examwarrior.R;

import static com.google.android.youtube.player.YouTubePlayer.*;


public class MainActivity extends YouTubeBaseActivity implements
		OnInitializedListener, PlayerStateChangeListener {

	private static final int RECOVERY_DIALOG_REQUEST = 1;

	// YouTube player view
	private YouTubePlayerView youTubeView;
	//RECYCLER VIEW FIELD
	RecyclerView recyclerView;
    public static YouTubePlayer youTubePlayer;
	//VECTOR FOR VIDEO URLS
	Vector<YoutubeVideo> youtubeVideos = new Vector<YoutubeVideo>();
	public static List<String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		list=new ArrayList<>();
		setContentView(R.layout.activtity_playerslist);
		youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

		// Initializing video player with developer key
		youTubeView.initialize(Config.DEVELOPER_KEY, this);

		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager( new LinearLayoutManager(this));

		//Load video List
		youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PlgSC4YeBjY\" frameborder=\"0\" allowfullscreen></iframe>"));
		youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/25A7k9P3XfQ\" frameborder=\"0\" allowfullscreen></iframe>") );
		youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/HrYCPopHZPQ\" frameborder=\"0\" allowfullscreen></iframe>") );
		youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WS2jxFG7h0w\" frameborder=\"0\" allowfullscreen></iframe>") );
		youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xzyWE2_91oI\" frameborder=\"0\" allowfullscreen></iframe>") );

		VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);
		list.add("PlgSC4YeBjY");
		list.add("25A7k9P3XfQ");
		list.add("HrYCPopHZPQ");
		list.add("xzyWE2_91oI");
		list.add("WS2jxFG7h0w");
		recyclerView.setAdapter(videoAdapter);

	}

	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult errorReason) {
		if (errorReason.isUserRecoverableError()) {
			errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
		} else {
			String errorMessage = String.format(
					getString(R.string.error_player), errorReason.toString());
			Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onInitializationSuccess(Provider provider,
			 YouTubePlayer player, boolean wasRestored) {
		youTubePlayer=player;
		if (!wasRestored) {

			// loadVideo() will auto play video
			// Use cueVideo() method, if you don't want to play it automatically
			//player.loadVideo(Config.YOUTUBE_VIDEO_CODE);
            player.loadVideos(list);

			// Hiding player controls
			player.setPlayerStyle(PlayerStyle.DEFAULT);

			//This flag tells the player to switch to landscape when in fullscreen, it will also return to portrait
			//when leaving fullscreen
			player.setFullscreenControlFlags(FULLSCREEN_FLAG_CONTROL_ORIENTATION);

			//This flag tells the player to automatically enter fullscreen when in landscape. Since we don't have
			//landscape layout for this activity, this is a good way to allow the user rotate the video player.
			player.addFullscreenControlFlag(FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);

			//This flag controls the system UI such as the status and navigation bar, hiding and showing them
			//alongside the player UI
			player.addFullscreenControlFlag(FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);


		}
		player.setPlayerStateChangeListener(this);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RECOVERY_DIALOG_REQUEST) {
			// Retry initialization if user performed a recovery action
			getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
		}
	}

	private Provider getYouTubePlayerProvider() {
		return (YouTubePlayerView) findViewById(R.id.youtube_view);
	}

	@Override
	public void onLoading() {
		if(!TextUtils.isEmpty("HrYCPopHZPQ") && youTubePlayer != null)
			youTubePlayer.play(); //auto play
	}

	@Override
	public void onLoaded(String s) {

	}

	@Override
	public void onAdStarted() {

	}

	@Override
	public void onVideoStarted() {

	}

	@Override
	public void onVideoEnded() {

	}

	@Override
	public void onError(ErrorReason errorReason) {

	}
}
