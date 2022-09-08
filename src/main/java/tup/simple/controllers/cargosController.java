package tup.simple.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tup.simple.models.cargos;
import tup.simple.repositories.cargosRepository;

@Controller // This means that this class is a Controller
// La URL que vaya en la anotación habrá que agregarla detrás del puerto :8080
// en todas las llamadas a esta aplicación.
// Por ejemplo @RequestMapping("/user") resultaría en lo siguiente:
// localhost:8080/anime.... y detrás de esto habría que agregar el
// resto de la URL.
// En este caso, no necesitamos nada, y queda simplemente localhost:8080
@RequestMapping("/cargos")
public class cargosController {
    // This means to get the bean called userRepository
    // which is auto-generated by Spring, we will use it to handle the data
    @Autowired
    private cargosRepository _cargosRepository;

    @PostMapping("/add") // Map ONLY POST Requests
    public @ResponseBody String addNewChargue(@RequestParam String cargo, @RequestParam Float salarioBasico,
    @RequestParam String jornada, @RequestParam String requisito,@RequestParam String modalidad) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        cargos n = new cargos();
        n.setCargo(cargo);
        n.setSalariobasico(salarioBasico);
        n.setJornada(jornada);
        n.setRequisito(requisito);
        n.setModalidad(modalidad);
        _cargosRepository.save(n);
        return "Saved";
    }

    @PostMapping("/delete/{id}") // Map ONLY POST Requests
    public @ResponseBody String deleteChargueById(@PathVariable Long id) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        _cargosRepository.deleteById(id);
        return "Deleted";
    }

    @GetMapping("/all")
    public @ResponseBody String getAllChargues() {
        // This returns a JSON or XML with the users
        Iterable<cargos> iterable = _cargosRepository.findAll();
        String resp = "<style>\n"
                +"#users {\n"
                +"  font-family: Arial, Helvetica, sans-serif;\n"
                +"  border-collapse: collapse;\n"
                +"  width: 100%;}\n"

                +"#users td, #users th {\n"
                +"  border: 1px solid #ddd;\n"
                +"  padding: 8px;}\n"

                +"#users tr:nth-child(even){background-color: #f2f2f2;}\n"

                +"#users tr:hover {background-color: #ddd;}\n"

                +"#users th {\n"
                +"  padding-top: 12px;\n"
                +"  padding-bottom: 12px;\n"
                +"  text-align: left;\n"
                +"  background-color: #04AA6D;\n"
                +"  color: white;}\n"
                +"</style>";
        resp += "<table id ='users'>"
                + "<tr>"
                + "<th>Id</th>"
                + "<th>Cargo</th>"
                + "<th>Salario basico</th>"
                + "<th>Jornada</th>"
                + "<th>Requisito</th>"
                + "<th>Modalidad</th>"
                + "</tr>";
        // No puedo usar forEach() con una función lambda
        // porque el scope de las variables no lo permite.
        for (cargos chargues : iterable) {
            resp += "<tr>"
                    + "<td>" + chargues.getId() + "</td>"
                    + "<td>" + chargues.getCargo() + "</td>"
                    + "<td>" + chargues.getSalariobasico() + "</td>"
                    + "<td>" + chargues.getJornada() + "</td>"
                    + "<td>" + chargues.getRequisito() + "</td>"
                    + "<td>" + chargues.getModalidad() + "</td>"
                    + "</tr>";
        }
        return resp + "</table>";
    }

    @GetMapping("")
    public @ResponseBody String hola() {
        return "Hola";
    }
}