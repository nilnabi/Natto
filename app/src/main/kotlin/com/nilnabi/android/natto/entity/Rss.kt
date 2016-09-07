package com.nilnabi.android.natto.entity

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

/**
 * Created by nilnabi on 2016/09/03.
 */
@Root(name = "rdf:RDF", strict = false)
class RssFeed {
    @set:ElementList(inline = true)
    @get:ElementList(inline = true)
    var list: List<RssItem> = mutableListOf()
}

@Root(name = "item", strict = false)
class RssItem {

    @set:Element
    @get:Element
    var title: String = ""

    @set:Element
    @get:Element
    var link: String = ""

    @set:Element(required = false)
    @get:Element(required = false)
    var description: String = ""

    @Path("dc/date")
    @set:Element(required = false)
    @get:Element(required = false)
    var date: String = ""

    fun getKey(): String {
        return link.replace(Regex("archives/.+"), "index.rdf")
    }

    fun toMapOf(): Map<String, Any> {
        return mapOf(
                Pair("rdf_url", link.replace(Regex("archives/.+"), "index.rdf")),
                Pair("title", title),
                Pair("link", link),
                Pair("description", description),
                Pair("date", date)
        )
    }
}