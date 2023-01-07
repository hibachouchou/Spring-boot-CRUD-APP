package com.hiba.produits;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.hiba.produits.entities.Categorie;
import com.hiba.produits.entities.Produit;
import com.hiba.produits.repos.ProduitRepository;
import com.hiba.produits.service.ProduitService;
@SpringBootTest
class ProduitsApplicationTests {
@Autowired
ProduitService produitService;
@Autowired
private ProduitRepository produitRepository;
@Test
public void testCreateProduit() {
Produit prod = new Produit("PC Dell",2200.500,new Date());
produitRepository.save(prod);
}
@Test
public void testFindProduit()
{
Produit p = produitRepository.findById(1L).get(); 
System.out.println(p);
}
@Test
public void testUpdateProduit()
{
Produit p = produitRepository.findById(2L).get();
p.setPrixProduit(1000.0);
produitRepository.save(p);
}
@Test
public void testDeleteProduit()
{
produitRepository.deleteById(3L);;
}
 
@Test
public void testListerTousProduits()
{
List<Produit> prods = produitRepository.findAll();
for (Produit p : prods)
{
System.out.println(p);
}
}
@Test
public void testFindByNomProduitContains()
{
Page<Produit> prods = produitService.getAllProduitsParPage(0,2);
System.out.println(prods.getSize());
System.out.println(prods.getTotalElements());
System.out.println(prods.getTotalPages());
prods.getContent().forEach(p -> {System.out.println(p.toString());
 });
/*ou bien
for (Produit p : prods)
{
System.out.println(p);
} */
}
@Test
public void testFindByNomProduit()
{
List<Produit> prods = produitRepository.findByNomProduit("PC Dell");
for (Produit p : prods)
{
System.out.println(p);
}
}
@Test
public void testFindByNomProduitContains1 ()
{
List<Produit> prods=produitRepository.findByNomProduitContains("PC Dell");
for (Produit p : prods)
{
System.out.println(p);
} }
@Test
public void testfindByCategorie()
{
Categorie cat = new Categorie();
cat.setIdCat(1L);
List<Produit> prods = produitRepository.findByCategorie(cat);
for (Produit p : prods)
{
System.out.println(p);
}
}
@Test
public void findByCategorieIdCat()
{
List<Produit> prods = produitRepository.findByCategorieIdCat(1L);
for (Produit p : prods)
{
System.out.println(p);
}
 }
@Test
public void testfindByOrderByNomProduitAsc()
{
List<Produit> prods = 
produitRepository.findByOrderByNomProduitAsc();
for (Produit p : prods)
{
System.out.println(p);
}
}
@Test
public void testTrierProduitsNomsPrix()
{
List<Produit> prods = produitRepository.trierProduitsNomsPrix();
for (Produit p : prods)
{
System.out.println(p);
}
}


}
