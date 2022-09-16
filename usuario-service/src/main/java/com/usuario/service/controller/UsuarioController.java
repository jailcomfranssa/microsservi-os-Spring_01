package com.usuario.service.controller;

import com.usuario.service.entity.Usuario;
import com.usuario.service.model.CarroModel;
import com.usuario.service.model.MotoModel;
import com.usuario.service.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuario(){
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario>obterUsuario(@PathVariable("id") int id){
        Usuario usuario = usuarioService.getUsuarioById(id);
        if(usuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);

    }

    @PostMapping
    public ResponseEntity<Usuario>salvarUsuario(@RequestBody Usuario usuario){
        Usuario salvarUsuario = usuarioService.seve(usuario);
        return ResponseEntity.ok(usuario);

    }

    @GetMapping("/carros/{usuarioId}")
    public ResponseEntity<List<CarroModel>> listarCarros(@PathVariable("usuarioId") int usuarioId){
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null){
            return ResponseEntity.notFound().build();
        }
        List<CarroModel> carros = usuarioService.getCarros(usuarioId);
        return ResponseEntity.ok(carros);

    }

    @GetMapping("/motos/{usuarioId}")
    public ResponseEntity<List<MotoModel>> listarMotos(@PathVariable("usuarioId") int usuarioId){
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null){
            return ResponseEntity.notFound().build();
        }
        List<MotoModel> motos = usuarioService.getMotos(usuarioId);
        return ResponseEntity.ok(motos);

    }
    // ************** FeignCliente *************************************
    @PostMapping("/carro/{usuarioId}")
    public ResponseEntity<CarroModel>guardaCarro(@PathVariable("usuarioId")int usuarioId,@RequestBody CarroModel carro){
        CarroModel novoCarro = usuarioService.saveCarro(usuarioId,carro);
        return ResponseEntity.ok(novoCarro);
    }

    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<MotoModel>guardaMoto(@PathVariable("usuarioId")int usuarioId,@RequestBody MotoModel moto){
        MotoModel novoMoto = usuarioService.saveMoto(usuarioId,moto);
        return ResponseEntity.ok(novoMoto);
    }

    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") int usuarioId){
        Map<String,Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
        return ResponseEntity.ok(resultado);
    }
}
