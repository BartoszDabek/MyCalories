package pl.mycalories.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.mycalories.model.AbstractModel;
import pl.mycalories.service.AbstractService;

import java.util.List;

public abstract class AbstractController<T extends AbstractModel, S extends AbstractService<T, Long>> {

    protected S service;

    public AbstractController(S service) {
        this.service = service;
    }

    @GetMapping
    public @ResponseBody
    ResponseEntity<List<T>> getAll() {
        List<T> objects = service.getAll();

        return new ResponseEntity<List<T>>(objects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<T> get(@PathVariable Long id) {
        T obj = service.findById(id);

        return new ResponseEntity<T>(obj, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public @ResponseBody
    ResponseEntity<?> create(@RequestBody T obj) {
        T createdObj = service.save(obj);

        return new ResponseEntity<T>(createdObj, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody T obj) {
        obj.setId(id);
        obj = service.save(obj);

        return new ResponseEntity<T>(obj, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseEntity<T> delete (@PathVariable Long id) {
        T obj = service.findById(id);

        service.delete(obj);
        return new ResponseEntity<T>(HttpStatus.NO_CONTENT);
    }

}
