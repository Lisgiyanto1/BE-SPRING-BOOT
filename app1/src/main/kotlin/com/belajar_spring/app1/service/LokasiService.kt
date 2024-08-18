package com.belajar_spring.app1.service

import com.belajar_spring.app1.model.Lokasi
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class LokasiService(val db: JdbcTemplate) {

    fun saveLokasi(lokasi: Lokasi) {
        db.update("INSERT INTO lokasi (nama_lokasi, negara, provinsi, kota) VALUES (?, ?, ?, ?)",
            lokasi.nama_lokasi, lokasi.negara, lokasi.provinsi, lokasi.kota)
    }

    fun findAllLokasi(): List<Lokasi> = db.query("SELECT * FROM lokasi") { rs, _ ->
        Lokasi(rs.getInt("id"), rs.getString("nama_lokasi"), rs.getString("negara"), rs.getString("provinsi"), rs.getString("kota"))
    }

fun updateLokasi(lokasi: Lokasi) {
    val sql = "UPDATE lokasi SET nama_lokasi = ?, negara = ?, provinsi = ?, kota = ? WHERE id = ?"
    db.update(sql, lokasi.nama_lokasi, lokasi.negara, lokasi.provinsi, lokasi.kota, lokasi.id)
}

fun deleteLokasi(id: Int) {
    db.update("DELETE FROM proyek_lokasi WHERE lokasi_id = ?", id)
    db.update("DELETE FROM lokasi WHERE id = ?", id)
}
}
