package com.usuario.service.feignClient;

import com.usuario.service.model.CarroModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "carro-service",url = "http://localhost:8002")
@RequestMapping("/carro")
public interface CarroFeignClient {
    @PostMapping()
    public CarroModel save(@RequestBody CarroModel carro);

    @GetMapping("usuario/{usuarioId}")
    public List<CarroModel> getCarros(@PathVariable("usuarioId") int usuarioId);
}
