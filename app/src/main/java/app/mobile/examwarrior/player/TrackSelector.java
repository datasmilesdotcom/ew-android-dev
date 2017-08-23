package app.mobile.examwarrior.player;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;

/**
 * Created by sandesh on 28/7/17, 10:18 PM.
 */

public class TrackSelector extends DefaultTrackSelector {

    @Override
    protected TrackSelection selectVideoTrack(RendererCapabilities rendererCapabilities, TrackGroupArray groups, int[][] formatSupport, int maxVideoWidth, int maxVideoHeight, int maxVideoBitrate, boolean allowNonSeamlessAdaptiveness, boolean allowMixedMimeAdaptiveness, int viewportWidth, int viewportHeight, boolean orientationMayChange, TrackSelection.Factory adaptiveTrackSelectionFactory, boolean exceedConstraintsIfNecessary, boolean exceedRendererCapabilitiesIfNecessary) throws ExoPlaybackException {
        return super.selectVideoTrack(rendererCapabilities, groups, formatSupport, maxVideoWidth, maxVideoHeight, maxVideoBitrate, allowNonSeamlessAdaptiveness, allowMixedMimeAdaptiveness, viewportWidth, viewportHeight, orientationMayChange, adaptiveTrackSelectionFactory, exceedConstraintsIfNecessary, exceedRendererCapabilitiesIfNecessary);
    }

    @Override
    protected TrackSelection selectOtherTrack(int trackType, TrackGroupArray groups, int[][] formatSupport, boolean exceedRendererCapabilitiesIfNecessary) {
        return super.selectOtherTrack(trackType, groups, formatSupport, exceedRendererCapabilitiesIfNecessary);
    }

    @Override
    protected TrackSelection[] selectTracks(RendererCapabilities[] rendererCapabilities, TrackGroupArray[] rendererTrackGroupArrays, int[][][] rendererFormatSupports) throws ExoPlaybackException {
        return super.selectTracks(rendererCapabilities, rendererTrackGroupArrays, rendererFormatSupports);
    }
}
