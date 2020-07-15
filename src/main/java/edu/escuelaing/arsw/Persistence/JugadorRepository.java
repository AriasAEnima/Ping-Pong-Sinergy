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
import edu.escuelaing.arsw.entities.Jugador;


import org.springframework.data.mongodb.repository.MongoRepository;


public interface JugadorRepository extends MongoRepository<Jugador, String> {

  public Jugador findByUsername(String nick);

}