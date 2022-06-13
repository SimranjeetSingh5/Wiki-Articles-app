package com.example.wikiarticlesapp.utils

class Constants {
    companion object {
        const val BASE_URL = "https://commons.wikimedia.org/"
        const val FEATURED_URL = "w/api.php?action=query" +
                "&prop=imageinfo&" +
                "iiprop=timestamp|user|url&" +
                "generator=categorymembers" +
                "&gcmtype=file" +
                "&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons" +
                "&format=json&utf8"
        const val ARTICLES_URL = "w/api.php?format=json&action=query&generator=ra" +
                "ndom&grnnamespace=0&prop=revisions|images&rvprop=content&grnlimit" +
                "=10"

        const val QUERY_PAGE_SIZE = 10

        const val CATEGORY_URL = "https://en.wikipedia.org/w/api.php?action=query&list=allcategories&acprefix=List+of&formatversion=2&format=json"
    }
}