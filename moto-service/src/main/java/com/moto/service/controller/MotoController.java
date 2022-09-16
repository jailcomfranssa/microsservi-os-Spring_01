package com.moto.service.controller;

import com.moto.service.entity.Moto;
import com.moto.service.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/moto")
public class MotoController {

    @Autowired
    private MotoService motoService;


    @GetMapping
    public ResponseEntity<List<Moto>> listarMoto(){
        List<Moto> motos = motoService.getAll();
        if(motos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Moto>buscarMoto(@PathVariable("id") int id){
        Moto moto = motoService.getCarroById(id);
        if(moto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moto);

    }

    @PostMapping
    public ResponseEntity<Moto> salvarMoto(@RequestBody Moto moto){
        Moto novoMoto = motoService.seve(moto);
        return ResponseEntity.ok(novoMoto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Moto>>listarMotoPorUsuarioId(@PathVariable("usuarioId") int usuarioId){
        List<Moto> motos = motoService.byUsuarioId(usuarioId);
        if(motos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motos);


    }




}
