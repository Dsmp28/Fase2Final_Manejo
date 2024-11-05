package org.java.fase2final_manejo.repositories;

import java.util.List;
import java.util.Optional;

public abstract class GenericRepository<T> {

    private String dataFilePath;
    private String indexFilePath;
    private Class<T> clazz;

    public GenericRepository(String dataFilePath, String indexFilePath, Class<T> clazz) {
        this.dataFilePath = dataFilePath;
        this.indexFilePath = indexFilePath;
        this.clazz = clazz;
    }

    public List<T> findAll() {
        return null;
    }

    public void save(T entity) {

    }

    public void deleteById(Long id){

    }

    public long count(){
        return 0;
    }

    public Optional<T> findById(Long id) {
        return null;
    }
}


