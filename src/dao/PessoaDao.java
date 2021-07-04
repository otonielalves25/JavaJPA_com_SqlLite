/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexao.ConexaoJPA;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import modelo.Pessoa;

/**
 *
 * @author Tony
 */
public class PessoaDao {
    // CRIANDO AS VARIAVERES DA CLASSES
    private EntityManager em;
    private EntityManagerFactory emf;

    // CRIA A FUNÇÃO PUBLICA PRA TODOS USAREM
    private EntityManager getEM() {
        emf = ConexaoJPA.getConexao(); //PEGANDO A CONEXÃO DA CLASSE CONEXAO
        return emf.createEntityManager();
    }

    // INSERIDO NOVO NO BANCO //////////////////////////////////////////////////
    public void inserir(Pessoa obj) {
        try {
            em = getEM();
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            
        } finally {
            em.close();
            
        }
        
    }

    /// ALTERAR ////////////////////////////////////////////////////////////////
    public void atualizar(Pessoa obj) {
        
        try {
            em = getEM();
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            
        } finally {
            em.close();
        }
    }

    // EXCLUIR /////////////////////////////////////////////////////////////////
    public void excluir(int codigo) {
        
        try {
            em = getEM();
            em.getTransaction().begin();
            Pessoa obj = em.find(Pessoa.class, codigo);
            em.remove(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // RETORNA POR ID //////////////////////////////////////////////////////////
    public Pessoa getPorID(int id) {
        em = getEM();
        Pessoa obj = em.find(Pessoa.class, id);
        em.close();
        return obj;
        
    }

    // RETORNA LISTAGEM DE TUDO  ///////////////////////////////////////////////
    public List<Pessoa> getListagem() {
        em = getEM();
        List<Pessoa> lista = em.createNamedQuery("Pessoa.findAll").getResultList();
        em.close();
        return lista;
    }

    // RETORNA LISTAGEM COM LIKE  //////////////////////////////////////////////
    public List<Pessoa> getListagemLike(String procura) {

        //MODELO - String sql = "SELECT c FROM Ciretran c JOIN C.tipoCiretranid t WHERE c.ciretran like ?1 OR t.tipoCiretran LIKE ?2 ORDER BY c.ciretran";        
        String sql = "";
        em = getEM();
        //Query query = em.createNamedQuery("Ciretran.findAllLike", Ciretran.class); // USAR QUANDO O NOME DA QUERY
        TypedQuery<Pessoa> query = em.createQuery(sql, Pessoa.class);
        //query.setParameter(1, "%" + procura + "%");
        //query.setParameter(2, "%" + procura + "%");
        List<Pessoa> results = query.getResultList();
        em.close();
        
        return results;
        
    }

    // VERIFICA SE JÁ TEM CADASTRO /////////////////////////////////////////////
    public boolean verificaCadastrado(String procura) {
        em = getEM();
        String sql = "FROM Pessoa x WHERE x = '" + procura + "'";
       // Query query = em.createQuery(sql);
        //List results = query.getResultList();
        em.close();
//        if (results.size() > 0) {
            return true;
//        } else {
//            return false;
//        }
        
    }
}
