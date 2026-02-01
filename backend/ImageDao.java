package pdl.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDao implements Dao<Image> {

  private final Map<Long, Image> images = new HashMap<>();

  public ImageDao() {
    // placez une image test.jpg dans le dossier "src/main/resources" du projet
    final ClassPathResource imgFile = new ClassPathResource("test.jpg");
    byte[] fileContent;
    try {
      fileContent = Files.readAllBytes(imgFile.getFile().toPath());
      Image img = new Image("logo.jpg", fileContent);
      images.put(img.getId(), img);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Optional<Image> retrieve(final long id) {
    // Ici on utilise la méthode ofNullable de Optional pour retourner un Optional
    // contenant la valeur de la map pour la clé id si elle existe, sinon null
    return Optional.ofNullable(images.get(id));
  }

  @Override
  public List<Image> retrieveAll() {
    // Ici ma première idée était de récuperer la valeur de la dernière clé en suite
    // faire une boucle
    // qui parcourerais tout les éléments de la map et ajouterait les valeurs dans
    // une liste via la fonction en haut
    // retrieve pour chaque élément. mais un socusi de performances s'impose surtout
    // si des vide existent au milieu ( suite a des supression)
    // on a donc utilisé la fonction image.values qui va nous donner une collection
    // de toutes les valeurs de la map
    // ensuite on a converti cette collection en une liste via le constructeur de
    // ArrayList
    return new ArrayList<Image>(images.values());
  }

  @Override
  public void create(final Image img) {
    // Ici pour ajouter une image a notre map, on va d'abord vérifier qu'elle n'est
    // pas null en suite
    // utiliser la fonction image.put pour ajouter l'image a la map
    // avec l'id de l'image en clé et l'image en valeur
    if (img != null) {
      images.put(img.getId(), img);
    }
  }

  @Override
  public void update(final Image img, final String[] params) {
    // Not used
  }

  @Override
  public void delete(final Image img) {
    // Ici pour supprimer une image de notre map, on va d'abord vérifier qu'elle
    // n'est pas null en suite
    // utiliser la fonction image.remove pour supprimer l'image de la map
    // avec l'id de l'image en clé
    if (img != null) {
      images.remove(img.getId());
    }
  }

}
