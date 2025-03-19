# Gestion des Patients - Application Spring MVC

## Description
Cette application Web JEE permet de gérer les patients à l'aide du framework Spring MVC, Thymeleaf pour la vue, et Spring Data JPA pour l'accès aux données.

## Technologies utilisées
- **Spring Boot** - Pour le développement rapide et la configuration automatique
- **Spring MVC** - Pour la gestion des contrôleurs et des vues
- **Thymeleaf** - Pour le rendu dynamique des pages HTML
- **Spring Data JPA** - Pour l'interaction avec la base de données
- **H2 Database** (ou autre SGBD) - Pour le stockage des données
- **Bootstrap** - Pour le design et le style des pages
- **MySQL** - Pour le stockage des données en production  


## Fonctionnalités
- **Afficher la liste des patients** avec pagination et recherche par nom
- **Ajouter un patient**
- **Supprimer un patient**
- **Modifier un patient** (non inclus dans le code actuel, mais facile à ajouter)
- **Navigation entre les pages** grâce à la pagination

## Structure du projet

### 1. `PatientController`
Ce contrôleur gère les requêtes liées aux patients.
- `@GetMapping("index")` : Affiche la liste paginée des patients avec une option de recherche
  ```java
  public String index(Model model, @RequestParam(name = "page" ,defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "4") int size,
                        @RequestParam(name = "keyword" ,defaultValue = "") String kw){
        Page<Patient> patientsList = patientService.getByName(kw,page, size);
        model.addAttribute("patientsList", patientsList.getContent());
        model.addAttribute("pages", new int[patientsList.getTotalPages()]);
        model.addAttribute("pagecount",page);
        model.addAttribute("keyword",kw);
        return "patients";
    }
  ```
- `@GetMapping("delet")` : Supprime un patient et redirige vers la liste des patients
- ```java
  public String delete(@RequestParam(name = "id") Long id,String keyword,int page){
        patientService.delete(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;

    }
  ```

### 2. `PatientService`
Service métier qui interagit avec le `PatientRepository`.
- `save(Patient patient)` : Enregistre un nouveau patient
  ```java
public  void save(Patient patient){
        patientRepository.save(patient);
    }
  ```
- `getById(Long id)` : Recherche un patient par ID
  ```java
public Patient getById(Long id){
        return patientRepository.findById(id).orElse(null);
    }
  ```
- `delete(Long id)` : Supprime un patient par ID
   ```java
public Patient delete(Long id){
        patientRepository.deleteById(id);
        return patientRepository.findById(id).orElse(null);
    }

  ```
- `getAll(int page, int size)` : Récupère une liste paginée de tous les patients
   ```java
ublic Page<Patient> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.findAll(pageable);
    }
  ```
- `getByName(String name, int page, int size)` : Recherche un patient par nom
   ```java
    public Page<Patient> getByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.findByNomContains(name, pageable);
    }

  ```

### 3. `PatientRepository`
Interface d'accès aux données utilisant **Spring Data JPA**.
- `findByNomContains(String nom, Pageable pageable)` : Recherche des patients par nom avec pagination

### 4. Page HTML `patients.html`
Utilise **Thymeleaf** pour afficher dynamiquement la liste des patients.
- Formulaire de recherche
  ```java
    <form th:action="@{/index}" method="get">
                    <div>Keyword</div>
                    <input type="text" name="keyword" th:value="${keyword}"/>
                    <button type="submit" class="btn btn-danger">
                        <i class="bi bi-search"></i>
                    </button>

                </form>
  ```
- Tableau affichant les patients
  ```java
  <tbody>
                    <tr th:each="p : ${patientsList}">
                        <td th:text="${p.id}"></td>
                        <td th:text="${p.nom}"></td>
                        <td th:text="${p.dateNaissance}"></td>
                        <td th:text="${p.malade}"></td>
                        <td th:text="${p.score}"></td>
                        <td>
                            <a onclick=" return confirm('Etes vous sure?')" th:href="@{delet(id=${p.id},keyword=${keyword},page=${pagecount})}" class="btn btn-danger">
                                <i class="bi bi-trash"></i>
                            </a>
                        </td>
                    </tr>
                    </tbody>
  ```
- Bouton pour supprimer un patient avec confirmation
  ```java
  <td>
                            <a onclick=" return confirm('Etes vous sure?')" th:href="@{delet(id=${p.id},keyword=${keyword},page=${pagecount})}" class="btn btn-danger">
                                <i class="bi bi-trash"></i>
                            </a>
                        </td>
  ```
- Système de pagination
  ```java
  <ul class="nav nav-pills">
                    <li th:each="value,item : ${pages}">
                     <a th:class="${pagecount == item.index?'btn btn-danger ms-2' : 'btn btn-outline-danger ms-2 '}" th:href="@{index(page=${item.index},keyword=${keyword})}"  th:text="${item.index}"></a>
                </ul>
  ```

## Installation et exécution
1. **Cloner le projet**
   ```bash
   git clone https://github.com/votre-repo/patient-management-spring-mvc.git
   cd patient-management-spring-mvc
   ```

2. **Configurer la base de données**
    `application.properties` pour utilisez MySQL
   ```java
    spring.datasource.url=jdbc:mysql://localhost:3306/hopital?createDatabaseIfNotExists=true
    spring.datasource.username=root
    spring.datasource.password=
    spring.datasource.hikari.maximum-pool-size=10
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   ```

4. **Lancer l'application**
   ```bash
   mvn spring-boot:run
   ```

5. **Accéder à l'application**
   - Ouvrir `http://localhost:8080/index` dans votre navigateur


---

Ce fichier README fournit une bonne documentation pour ton projet et permet à n'importe quel développeur de comprendre et d'exécuter ton application facilement.

