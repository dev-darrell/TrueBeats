package com.adnahcodes.truebeats.model

import com.google.gson.annotations.SerializedName

class ListOfTracks(
    @SerializedName("data")
    val data: List<Track>,
    @SerializedName("total")
    val totalLength: Int) {
    /** Instead of getting a link to the next set of tracks and having to make a complex API request
     * to get the rest of the list from here, add the trackCount gotten from the playlist as a limit
     * to the API call. That will ensure all the items get sent at once. Paging can then be used to
     * economise data, memory and battery life.
     */
}