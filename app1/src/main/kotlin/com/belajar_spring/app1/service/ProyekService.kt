package com.belajar_spring.app1.service

import com.belajar_spring.app1.model.Proyek
import com.belajar_spring.app1.model.ProyekWithLokasi
import com.belajar_spring.app1.model.Lokasi
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import org.springframework.beans.factory.annotation.Autowired
@Service
@Transactional
class ProyekService(val db: JdbcTemplate) {
 @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
fun saveProyek(proyek: Proyek) {
    val sql = "INSERT INTO proyek (nama_proyek, client, tgl_mulai, tgl_selesai, pimpinan_proyek, keterangan, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)"
    db.update(sql, proyek.nama_proyek, proyek.client, proyek.tgl_mulai, proyek.tgl_selesai, proyek.pimpinan_proyek, proyek.keterangan, LocalDateTime.now())

    val proyekId = db.queryForObject("SELECT LAST_INSERT_ID()", Int::class.java)

    // Check if the lokasi_id exists in the lokasi table
    val lokasiId = proyek.lokasi_id
    val lokasiExists = db.queryForObject("SELECT COUNT(*) FROM lokasi WHERE id = ?", Int::class.java, lokasiId) > 0

    if (lokasiExists) {
        val sqlProyekLokasi = "INSERT INTO proyek_lokasi (proyek_id, lokasi_id) VALUES (?, ?)"
        db.update(sqlProyekLokasi, proyekId, lokasiId)
    } else {
        // Handle the case where the lokasi_id does not exist in the lokasi table
        throw RuntimeException("Lokasi with ID $lokasiId does not exist")
    }
}

fun findAllProyek(): List<ProyekWithLokasi> {
    val sql = """
        SELECT p.id as proyek_id, p.nama_proyek, p.client, p.tgl_mulai, p.tgl_selesai, p.pimpinan_proyek, p.keterangan, 
               l.id as lokasi_id, l.nama_lokasi, l.negara, l.provinsi, l.kota
        FROM proyek p
        JOIN proyek_lokasi pl ON p.id = pl.proyek_id
        JOIN lokasi l ON pl.lokasi_id = l.id
    """
    return db.query(sql, ProyekWithLokasiRowMapper())
}
fun findProyekById(id: Int): Proyek? {
    val query = """
        SELECT p.id AS proyek_id, p.nama_proyek, p.client, p.tgl_mulai, p.tgl_selesai, p.pimpinan_proyek, p.keterangan, 
               l.id AS lokasi_id, l.nama_lokasi, l.negara, l.provinsi, l.kota 
        FROM proyek p 
        JOIN lokasi l ON p.id_lokasi = l.id 
        WHERE p.id = ?
    """.trimIndent()

    return jdbcTemplate.queryForObject(query, arrayOf(id), Proyek::class.java)
}

fun updateProyek(proyek: Proyek) {
    val sql = "UPDATE proyek SET nama_proyek = ?, client = ?, tgl_mulai = ?, tgl_selesai = ?, pimpinan_proyek = ?, keterangan = ? WHERE id = ?"
    db.update(sql, proyek.nama_proyek, proyek.client, proyek.tgl_mulai, proyek.tgl_selesai, proyek.pimpinan_proyek, proyek.keterangan, proyek.id)

    // Update the proyek_lokasi table separately
    val sqlProyekLokasi = "UPDATE proyek_lokasi SET lokasi_id = ? WHERE proyek_id = ?"
    db.update(sqlProyekLokasi, proyek.lokasi_id, proyek.id)
}

fun deleteProyek(id: Int) {
    // Delete child rows in proyek_lokasi table
    val sqlProyekLokasi = "DELETE FROM proyek_lokasi WHERE proyek_id = ?"
    db.update(sqlProyekLokasi, id)

    // Delete parent row in proyek table
    val sqlProyek = "DELETE FROM proyek WHERE id = ?"
    db.update(sqlProyek, id)
}
}

class ProyekWithLokasiRowMapper : RowMapper<ProyekWithLokasi> {
    override fun mapRow(rs: ResultSet, rowNum: Int): ProyekWithLokasi {
        val proyek = Proyek(rs.getInt("proyek_id"), rs.getString("nama_proyek"), rs.getString("client"),
            rs.getString("tgl_mulai"), rs.getString("tgl_selesai"), rs.getString("pimpinan_proyek"), rs.getString("keterangan"),
            rs.getInt("lokasi_id"))
        val lokasi = Lokasi(rs.getInt("lokasi_id"), rs.getString("nama_lokasi"), rs.getString("negara"),
            rs.getString("provinsi"), rs.getString("kota"))
        return ProyekWithLokasi(proyek, lokasi)
    }
}