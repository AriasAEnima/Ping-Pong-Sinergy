/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.Persistence;

/**
 *
 * @author J. Eduardo Arias
 */
import edu.escuelaing.arsw.entities.Partida;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartidaRepository extends MongoRepository<Partida, String> {

  

}