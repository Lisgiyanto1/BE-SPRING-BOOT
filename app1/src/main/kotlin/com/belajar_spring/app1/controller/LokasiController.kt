package com.belajar_spring.app1.controller

import com.belajar_spring.app1.model.Lokasi
import com.belajar_spring.app1.service.LokasiService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/lokasi")
class LokasiController(val lokasiService: LokasiService) {

    @PostMapping
    fun addLokasi(@RequestBody lokasi: Lokasi) {
        lokasiService.saveLokasi(lokasi)
    }

    @GetMapping
    fun getAllLokasi(): List<Lokasi> = lokasiService.findAllLokasi()

@PutMapping("/{id}")
fun updateLokasi(@PathVariable("id") id: Int, @RequestBody lokasiRequest: Lokasi) {
     val lokasi = lokasiRequest.copy(id = id)
    lokasiService.updateLokasi(lokasi)
}
    @DeleteMapping("/{id}")
    fun deleteLokasi(@PathVariable id: Int) {
        lokasiService.deleteLokasi(id)
    }
}
