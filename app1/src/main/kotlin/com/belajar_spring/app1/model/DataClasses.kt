package com.belajar_spring.app1.model

data class Lokasi(val id: Int?, val nama_lokasi: String, val negara: String, val provinsi: String, val kota: String)

data class Proyek(val id: Int?, val nama_proyek: String, val client: String, val tgl_mulai: String, val tgl_selesai: String, val pimpinan_proyek: String, val keterangan: String, val lokasi_id: Int)

data class ProyekWithLokasi(val proyek: Proyek, val lokasi: Lokasi)
