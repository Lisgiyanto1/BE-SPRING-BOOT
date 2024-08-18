package com.belajar_spring.app1.controller

import com.belajar_spring.app1.model.Proyek
import com.belajar_spring.app1.model.ProyekWithLokasi
import com.belajar_spring.app1.service.ProyekService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/proyek")
class ProyekController(val proyekService: ProyekService) {

    @PostMapping
    fun addProyek(@RequestBody proyek: Proyek) {
        proyekService.saveProyek(proyek)
    }

    @GetMapping
    fun getAllProyek(): List<ProyekWithLokasi> = proyekService.findAllProyek()

@GetMapping("/proyek/{id}")
fun findProyekById(@PathVariable id: Int): Proyek? {
    return proyekService.findProyekById(id)
}

@PutMapping("/{id}")
fun updateProyek(@PathVariable id: Int, @RequestBody proyekRequest: Proyek) {
    val proyek = proyekRequest.copy(id = id)
    proyekService.updateProyek(proyek)
}

    @DeleteMapping("/{id}")
    fun deleteProyek(@PathVariable id: Int) {
        proyekService.deleteProyek(id)
    }
}