package com.usuario.service.servicio;

import com.usuario.service.entity.Usuario;
import com.usuario.service.feignClient.CarroFeignClient;
import com.usuario.service.feignClient.MotoFeignClient;
import com.usuario.service.model.CarroModel;
import com.usuario.service.model.MotoModel;
import com.usuario.service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarroFeignClient carroFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(int id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario seve(Usuario usuario){
        Usuario novoUsuario = usuarioRepository.save(usuario);
        return novoUsuario;

    }

    //   ******** USANDO REST_TEMPLATE  *******************
     public List<CarroModel> getCarros(int usuarioId){
        List<CarroModel> carros = restTemplate.getForObject("http://localhost:8002/carro/usuario/"+usuarioId,List.class);
        return carros;

    }
    //   ******** USANDO REST_TEMPLATE  *******************
    public List<MotoModel> getMotos(int usuarioId){
        List<MotoModel> motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/"+usuarioId,List.class);
        return motos;

    }

    // ************** FeignCliente *************************************

    public CarroModel saveCarro(int usuarioId, CarroModel carro){
        carro.setUsuarioId(usuarioId);
        CarroModel novoCarro = carroFeignClient.save(carro);
        return novoCarro;
    }

    public MotoModel saveMoto(int usuarioId, MotoModel moto){
        moto.setUsuarioId(usuarioId);
        MotoModel novoMoto = motoFeignClient.save(moto);
        return novoMoto;
    }

    public Map<String, Object> getUsuarioAndVehiculos(int usuarioId){
        Map<String,Object> resultado = new HashMap<>();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if(usuario == null) {
            resultado.put("Mensagem", "Usuario não existe");
            return resultado;
        }

        resultado.put("Usuario",usuario);
        List<CarroModel> carros = carroFeignClient.getCarros(usuarioId);
        if(carros.isEmpty()) {
            resultado.put("Carros", "Usuario não tem carro");
        }
        else {
            resultado.put("Carro", carros);
        }

        List<MotoModel> motos = motoFeignClient.getMotos(usuarioId);
        if(motos.isEmpty()) {
            resultado.put("Motos", "Usuario não tem moto");
        }
        else {
            resultado.put("Moto", motos);
        }
        return resultado;
    }




}
