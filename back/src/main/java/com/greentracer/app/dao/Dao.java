package com.greentracer.app.dao;

import java.util.List;

/**
 * Interface pour les Dao.
 * @param <U> U le type d'identifiant.
 * @param <T> T le type d'objet concerné.
 */
public interface Dao<U, T> {
    /**
     * Retourne l'objet correspondant à l'objet en paramètre.
     * @param id
     * @return
     * @throws IllegalArgumentException
     */
    T getById(U id) throws IllegalArgumentException;

    /**
     * Retourne une liste d'objet T.
     */
    List<T> getAll();

    /**
     * Supprime l'objet en paramètre.
     * @param obj
     * @return vrai si suppression effective, faux sinon.
     */
    Boolean delete(T obj);

    /**
     * Met à jour l'objet en paramètre.
     * @param obj
     * @return
     */
    Boolean update(T obj);

    /**
     * Créé un objet dans la base de données basé sur l'objet en paramètre.
     * @param obj
     * @return
     */
    Boolean create(T obj);
}
