package dao;

import entities.ElementoCatalogo;
import entities.Libro;
import entities.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class CatalogoDao {

    private EntityManager em;

    public CatalogoDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
        em = emf.createEntityManager();
    }

    // Metodo per salvare un elemento nel catalogo
    public void save(ElementoCatalogo elemento) {
        em.getTransaction().begin();
        em.persist(elemento);
        em.getTransaction().commit();
    }

    // Metodo per cercare un elemento per ISBN
    public ElementoCatalogo getByISBN(String isbn) {
        return em.find(ElementoCatalogo.class, isbn);
    }

    // Metodo per cercare elementi per anno di pubblicazione

    public List<ElementoCatalogo> cercaPerAnno(int anno) {
        return em.createQuery(
                        "SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :anno", ElementoCatalogo.class)
                .setParameter("anno", anno)
                .getResultList();
    }

    // Metodo per cercare libri per autore

    public List<Libro> cercaPerAutore(String autore) {
        return em.createQuery(
                        "SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class)
                .setParameter("autore", autore)
                .getResultList();
    }

    // Metodo per cercare elementi per titolo

    public List<ElementoCatalogo> cercaPerTitolo(String titoloParziale) {
        return em.createQuery(
                        "SELECT e FROM ElementoCatalogo e WHERE LOWER(e.titolo) LIKE LOWER(:titolo)", ElementoCatalogo.class)
                .setParameter("titolo", "%" + titoloParziale + "%")
                .getResultList();
    }

    // Metodo per cercare prestiti attivi di un utente

    public List<Prestito> cercaPrestitiPerUtente(Long numeroTessera) {
        return em.createQuery(
                        "SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :num AND p.dataRestituzioneEffettiva IS NULL", Prestito.class)
                .setParameter("num", numeroTessera)
                .getResultList();
    }

    // Metodo per cercare prestiti scaduti non ancora restituiti

    public List<Prestito> cercaPrestitiScaduti() {
        return em.createQuery(
                        "SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < :oggi AND p.dataRestituzioneEffettiva IS NULL", Prestito.class)
                .setParameter("oggi", LocalDate.now())
                .getResultList();
    }

    // Metodo remove
    public void remove(String isbn) {
        ElementoCatalogo e = getByISBN(isbn);
        if (e != null) {
            em.getTransaction().begin();
            em.remove(e);
            em.getTransaction().commit();
        } else {
            System.out.println("Elemento con ISBN " + isbn + " non trovato.");
        }
    }
}