package com.usuario.service.feignClient;

import com.usuario.service.model.MotoModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "moto-service",url = "http://localhost:8003")
@RequestMapping("/moto")
public interface MotoFeignClient {

    @PostMapping()
    public MotoModel save(@RequestBody MotoModel moto);

    @GetMapping("usuario/{usuarioId}")
    public List<MotoModel> getMotos(@PathVariable("usuarioId") int usuarioId);
}
