package com.sudansh.music.datasource

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.sudansh.music.model.Lyric
import com.sudansh.music.model.Lyrics
import org.json.JSONArray
import java.util.concurrent.TimeUnit

class MediaDataSourceImp(private val context: Context) : MediaDataSource {
    override fun getMedias(): List<MediaItem> {
        val file = context.assets.open("media.json")
        val json = file.bufferedReader().use { it.readText() }
        val array = JSONArray(json)

        return (0 until array.length()).map { i ->
            val obj = array.getJSONObject(i)
            val name = obj.getString("name")
            val mediaId = obj.getString("media_id")
            val artist = obj.getString("artist")
            val artwork = Uri.parse(obj.getString("artwork"))
            val uri = Uri.parse(obj.getString("uri"))
            val subtitleUri = obj.getString("subtitle_uri")
            val subtitleMimeType = obj.getString("subtitle_mime_type")
            val year = obj.getString("year").toInt()

            val subtitle =
                MediaItem.Subtitle(
                    Uri.parse(subtitleUri),
                    subtitleMimeType,
                    null,
                    C.SELECTION_FLAG_FORCED
                )

            val metadata = MediaMetadata.Builder()
                .setTitle(name)
                .setArtist(artist)
                .setArtworkUri(artwork)
                .setMediaUri(uri)
                .setYear(year)
                .build()

            MediaItem.Builder()
                .setMediaId(mediaId)
                .setUri(uri)
                .setSubtitles(listOf(subtitle))
                .setMediaMetadata(metadata)
                .build()
        }
    }

    override fun getLyrics(mediaId: String, title: String): Lyrics {
        val lyricsFile =
            context.assets.open("${mediaId}.txt").bufferedReader().use { it.readText() }

        val lyrics = lyricsFile.split("\n")
            .filter { it.isNotBlank() }
            .map { line ->
                val hhmm = line.substring(line.indexOf("[") + 2, line.indexOf("]")).split(":")
                val mins = hhmm.first().toLongOrNull() ?: 0L
                val secs = hhmm[1].split(".").first().toLongOrNull() ?: 0L

                val timeInMillis: Long =
                    TimeUnit.MINUTES.toMillis(mins) + TimeUnit.SECONDS.toMillis(secs)

                val subtitle = try {
                    line.substring(line.indexOf("]") + 1)
                } catch (e: StringIndexOutOfBoundsException) {
                    ""
                }
                Lyric(timeInMillis, subtitle)
            }
            .filter { it.value.isNotBlank() }

        return Lyrics(lyrics)
    }
}