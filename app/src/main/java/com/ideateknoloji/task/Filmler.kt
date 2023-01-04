package com.ideateknoloji.task

data class Filmler(
    var rank: Int,
    var title: String,
    var thumbnail: String,
    var rating: String,
    var id : String,
    var year: Int,
    var image: String,
    var description: String,
    var trailer: String,
    var genre: ArrayList<String>,
    var director: ArrayList<String>,
    var writers: ArrayList<String>,
    var imbdbid: String,
)