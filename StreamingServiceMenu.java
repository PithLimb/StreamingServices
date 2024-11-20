package ci701.demo;

import java.io.*;  //This import is for saving and leading the objects to and from the files
import java.util.HashMap;  // Hashmap and Map used to store a collection of films
import java.util.Map;
import java.util.Scanner; // Lets the programme read user inputs

class Film implements Serializable { //Implements is used here to write and read the file in binary form
    String name;
    int year;
    String genre;
    double rating;
    int runtime;

    public Film(String name, int year, String genre, double rating, int runtime) {
        this.name = name;  // (This.) used to refer to the object itself
        this.year = year;
        this.genre = genre;
        this.rating = rating;
        this.runtime = runtime;
    }

    @Override
    public String toString() {  // toString let me read the code without "cryptic messages"
        return name + " (" + year + ") - " + genre + " - " + rating + " stars - " + runtime + " min";  // Return is used to exit from method
    }
}

class Season implements Serializable {
    int seasonNumber;
    int year;
    int numEpisodes;

    public Season(int seasonNumber, int year, int numEpisodes) {  // Public class so that it can be accessed anywhere in the code
        this.seasonNumber = seasonNumber;
        this.year = year;
        this.numEpisodes = numEpisodes;
    }

    @Override
    public String toString() {
        return "Season " + seasonNumber + " (" + year + ") - " + numEpisodes + " episodes";
    }
}

class TVShow implements Serializable {
    String name;
    String genre;
    double rating;
    Map<Integer, Season> seasons;

    public TVShow(String name, String genre, double rating) {
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.seasons = new HashMap<>();
    }

    public void addSeason(Season season) {  // void allows code execution without giving a return value
        seasons.put(season.seasonNumber, season);
    }

    public void listSeasons() {
        for (Season season : seasons.values()) {
            System.out.println(season);
        }
    }

    @Override
    public String toString() {
        return name + " - " + genre + " - " + rating + " stars";
    }
}

class StreamingService implements Serializable {
    String name;
    double price;
    Map<String, Film> films;
    Map<String, TVShow> tvShows;

    public StreamingService(String name, double price) {
        this.name = name;
        this.price = price;
        this.films = new HashMap<>();
        this.tvShows = new HashMap<>();
    }

    public void addFilm(Film film) {
        String key = film.name + film.year;  // String key to uniquely identify and manage the objects in a map
        if (!films.containsKey(key)) {  // checks if a film already exists
            films.put(key, film);
        } else {
            System.out.println("Film already exists.");  // if statement used here to prevent duplicates
        }
    }

    public void addTVShow(TVShow show) {
        if (!tvShows.containsKey(show.name)) {
            tvShows.put(show.name, show);
        } else {
            System.out.println("TV Show already exists.");
        }
    }

    public void listFilms() {
        for (Film film : films.values()) {
            System.out.println(film);
        }
    }

    public void listTVShows() {
        for (TVShow show : tvShows.values()) {
            System.out.println(show);
        }
    }

    public void rateFilm(String filmName, int year, double rating) {
        String key = filmName + year;
        Film film = films.get(key);
        if (film != null) {  // Means this thing is not empty so the print
            film.rating = rating;
            System.out.println("Rating updated for film: " + filmName);
        } else {
            System.out.println("Film not found.");
        }
    }

    public void rateTVShow(String showName, double rating) {
        TVShow show = tvShows.get(showName);
        if (show != null) {
            show.rating = rating;
            System.out.println("Rating updated for TV show: " + showName);
        } else {
            System.out.println("TV Show not found.");
        }
    }
}

public class StreamingServiceMenu {
    private static final String FILE_NAME = "streaming_service_data.ser";  // Keeps values secure inside the class

    public static void main(String[] args) {
        Map<String, StreamingService> services = loadData();
        if (services == null) {
            services = new HashMap<>();
        }

        Scanner scanner = new Scanner(System.in);  // creates a tool to read the input of the user in the console

        while (true) {
            System.out.println("\n--- Streaming Service Menu ---");
            System.out.println("1. Create a new streaming service");
            System.out.println("2. Add a film to a streaming service");
            System.out.println("3. Add a TV show to a streaming service");
            System.out.println("4. List all films in a service");
            System.out.println("5. List all TV shows in a service");
            System.out.println("6. Rate a film");
            System.out.println("7. Rate a TV show");
            System.out.println("8. Remove a film");
            System.out.println("9. Remove a TV show");
            System.out.println("10. Exit and Save");

            System.out.print("Enter your choice (1-10): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {  // switch over if-else as it is easier to read and prevents confusion with else if conditions
                case 1 -> {
                    System.out.print("Enter the service name: ");
                    String serviceName = scanner.nextLine();
                    System.out.print("Enter the monthly price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();

                    services.put(serviceName, new StreamingService(serviceName, price));
                    System.out.println("Streaming service created.");
                }
                case 2 -> {
                    System.out.print("Enter the service name: ");
                    String serviceName = scanner.nextLine();
                    StreamingService service = services.get(serviceName);
                    if (service != null) {
                        System.out.print("Enter film name: ");
                        String filmName = scanner.nextLine();
                        System.out.print("Enter release year: ");
                        int year = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter genre: ");
                        String genre = scanner.nextLine();
                        System.out.print("Enter rating (0-5): ");
                        double rating = scanner.nextDouble();
                        System.out.print("Enter runtime (minutes): ");
                        int runtime = scanner.nextInt();
                        scanner.nextLine();

                        service.addFilm(new Film(filmName, year, genre, rating, runtime));
                        System.out.println("Film added.");
                    } else {
                        System.out.println("Service not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter the service name: ");
                    String serviceName = scanner.nextLine();
                    StreamingService service = services.get(serviceName);
                    if (service != null) {
                        System.out.print("Enter TV show name: ");
                        String showName = scanner.nextLine();
                        System.out.print("Enter genre: ");
                        String genre = scanner.nextLine();
                        System.out.print("Enter rating (0-5): ");
                        double rating = scanner.nextDouble();
                        scanner.nextLine();

                        service.addTVShow(new TVShow(showName, genre, rating));
                        System.out.println("TV show added.");
                    } else {
                        System.out.println("Service not found.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter the service name: ");
                    String serviceName = scanner.nextLine();
                    StreamingService service = services.get(serviceName);
                    if (service != null) {
                        System.out.println("Films:");
                        service.listFilms();
                    } else {
                        System.out.println("Service not found.");
                    }
                }
                case 5 -> {
                    System.out.print("Enter the service name: ");
                    String serviceName = scanner.nextLine();
                    StreamingService service = services.get(serviceName);
                    if (service != null) {
                        System.out.println("TV Shows:");
                        service.listTVShows();
                    } else {
                        System.out.println("Service not found.");
                    }
                }
                case 6 -> {
                    System.out.print("Enter the service name: ");
                    String serviceName = scanner.nextLine();
                    StreamingService service = services.get(serviceName);
                    if (service != null) {
                        System.out.print("Enter film name: ");
                        String filmName = scanner.nextLine();
                        System.out.print("Enter release year: ");
                        int year = scanner.nextInt();
                        System.out.print("Enter new rating (0-5): ");
                        double rating = scanner.nextDouble();
                        scanner.nextLine();

                        service.rateFilm(filmName, year, rating);
                    } else {
                        System.out.println("Service not found.");
                    }
                }
                case 7 -> {
                    System.out.print("Enter the service name: ");
                    String serviceName = scanner.nextLine();
                    StreamingService service = services.get(serviceName);
                    if (service != null) {
                        System.out.print("Enter TV show name: ");
                        String showName = scanner.nextLine();
                        System.out.print("Enter new rating (0-5): ");
                        double rating = scanner.nextDouble();
                        scanner.nextLine();

                        service.rateTVShow(showName, rating);
                    } else {
                        System.out.println("Service not found.");
                    }
                }
                case 8 -> {
                    System.out.print("Enter the service name: ");
                    String serviceName = scanner.nextLine();
                    StreamingService service = services.get(serviceName);
                    if (service != null) {
                        System.out.print("Enter film name: ");
                        String filmName = scanner.nextLine();
                        System.out.print("Enter release year: ");
                        int year = scanner.nextInt();
                        scanner.nextLine();
                        if (service.films.remove(filmName + year) != null) {
                            System.out.println("Film removed.");
                        } else {
                            System.out.println("Film not found.");
                        }
                    } else {
                        System.out.println("Service not found.");
                    }
                }
                case 9 -> {
                    System.out.print("Enter the service name: ");
                    String serviceName = scanner.nextLine();
                    StreamingService service = services.get(serviceName);
                    if (service != null) {
                        System.out.print("Enter TV show name: ");
                        String showName = scanner.nextLine();
                        if (service.tvShows.remove(showName) != null) {
                            System.out.println("TV show removed.");
                        } else {
                            System.out.println("TV show not found.");
                        }
                    } else {
                        System.out.println("Service not found.");
                    }
                }
                case 10 -> {
                    saveData(services);
                    System.out.println("Data saved. Exiting.");
                    return;  // Exit the program
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void saveData(Map<String, StreamingService> services) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(services);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static Map<String, StreamingService> loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (Map<String, StreamingService>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved data found or error loading data.");
            return null;
        }
    }
}