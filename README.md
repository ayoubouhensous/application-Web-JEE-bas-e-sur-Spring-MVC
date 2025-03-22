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
- **Modifier un patient**
- **Supprimer un patient**
- **Navigation entre les pages** grâce à la pagination
- **Utilisation de templates Thymeleaf avec des fragments** pour un meilleur réemploi du code

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
- `@GetMapping("/addPatient") : Affiche le formulaire d'ajout d'un patient
  ```java
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }
  ```
- `    @PostMapping("/savePatient")` : Enregistre un nouveau patient dans la base de données
  ```java
    public String savePatient(@Valid  Patient patient,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addPatient";
        }
        patientService.save(patient);
        return "redirect:/index";
  ```
- `@GetMapping("/editPatient/{id}")` : Charge les détails d'un patient pour modification
  ```java
      public String showEditPatientForm(@PathVariable Long id, Model model) {
              Patient patient = patientService.getById(id);
              if (patient == null) {
                  return "redirect:/index";
              }
              model.addAttribute("patient", patient);
              return "editPatient";
          }
  ```
- `@PostMapping("update")` : Met à jour les informations d'un patient existant
  ```java
    public String updatePatient( @Valid Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editPatient";
        }
        patientService.save(patient);
        return "redirect:/index";
    }

  ```
- `@GetMapping("delete")` : Supprime un patient et redirige vers la liste des patients
  ```java
  public String delete(@RequestParam(name = "id") Long id,String keyword,int page){
        patientService.delete(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;

    }

  ```

### 2. `PatientService`
Service métier qui interagit avec le `PatientRepository`.
- `save(Patient patient)` : Enregistre un nouveau patient
- `getById(Long id)` : Recherche un patient par ID
- `delete(Long id)` : Supprime un patient par ID
- `getAll(int page, int size)` : Récupère une liste paginée de tous les patients
- `getByName(String name, int page, int size)` : Recherche un patient par nom

### 3. `PatientRepository`
Interface d'accès aux données utilisant **Spring Data JPA**.
- `findByNomContains(String nom, Pageable pageable)` : Recherche des patients par nom avec pagination

### 4. Templates Thymeleaf avec Fragments
L'application utilise des fragments pour une meilleure organisation des templates :
- `layout.html` : Template principal contenant l'entête, le menu et le pied de page.
- `patients.html` : Affichage de la liste des patients.
- `add-patient.html` : Formulaire d'ajout d'un patient.
- `edit-patient.html` : Formulaire de modification d'un patient.
- `fragments/navbar.html` : Barre de navigation commune à toutes les pages.

### Exemple d'utilisation des fragments
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"  xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
  <link rel="stylesheet" href="/webjars/bootstrap/5.3.2/css/bootstrap.min.css"/>
  <link rel="stylesheet" href="/webjars/bootstrap-icons/1.11.3/font/bootstrap-icons.css"/>


</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" th:href="@{/index}">Home</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Patients
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" th:href="@{/addPatient}">Nouveau</a></li>
            <li><a class="dropdown-item" th:href="@{/index}">chercher</a></li>
          </ul>
        </li>
      </ul>
      <ul class="navbar-nav ms-auto">
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Username
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" th:href="@{/addPatient}">Lougout</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>
<div class="container mt-4">
    <section layout:fragment="content"></section>
     </div>
      <script src="/webjars/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>

</body>
</html>
```

## Installation et exécution
1. **Cloner le projet**
   ```bash
   git clone https://github.com/votre-repo/patient-management-spring-mvc.git
   cd patient-management-spring-mvc
   ```

2. **Configurer la base de données**
    `application.properties` pour utiliser MySQL
   ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/hopital?createDatabaseIfNotExists=true
    spring.datasource.username=root
    spring.datasource.password=
    spring.datasource.hikari.maximum-pool-size=10
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   ```

3. **Lancer l'application**
   ```bash
   mvn spring-boot:run
   ```

4. **Accéder à l'application**
   - Ouvrir `http://localhost:8080/index` dans votre navigateur

---
Ce fichier README est maintenant à jour avec les fonctionnalités d'ajout et d'édition de patient ainsi que l'utilisation des templates Thymeleaf avec fragments.

