//package UI;
//import Domain.Client;
//import Domain.Movie;
//import Domain.Reservation;
//import Service.ClientService;
//import Service.MovieService;
//import Service.ReservationService;
//import com.sun.org.apache.xpath.internal.operations.Bool;
//
//import java.util.Scanner;
//
//
//public class NewConsole {
//    private MovieService movieService;
//    private ClientService clientService;
//    private ReservationService reservationService;
//    private Scanner scanner;
//
//    public NewConsole(MovieService movieService, ClientService clientService, ReservationService reservationService) {
//        this.movieService = movieService;
//        this.clientService = clientService;
//        this.reservationService = reservationService;
//        this.scanner = new Scanner(System.in);
//    }
//
//
//    public void run() {
//        while (true) {
//            System.out.println("Alegeti optiunea, x pentru exit");
//            String option = scanner.nextLine();
//            String s[] = null;
//            s = option.split(",");
//
//            switch (s[0]) {
//                case "add movie":{
//                    movieService.addOrUpdate( s[1],  s[2],  Integer.parseInt(s[3]),  Double.parseDouble(s[4]), Boolean.parseBoolean(s[5]));
//                    break;
//                }
//                case "add client": {
//                    clientService.addOrUpdate(s[1], s[2], s[3], s[4], s[5], s[6], Integer.parseInt(s[7]));
//                    break;
//                }
//                case "add reservation": {
//                    reservationService.addOrUpdate(s[1], s[2], s[3], s[4], s[5]);
//                    break;
//                }
//                case "update movie":{
//                    movieService.addOrUpdate( s[1],  s[2],  Integer.parseInt(s[3]),  Double.parseDouble(s[4]), Boolean.parseBoolean(s[5]));
//                    break;
//                }
//                case "update client": {
//                    clientService.addOrUpdate(s[1], s[2], s[3], s[4], s[5], s[6], Integer.parseInt(s[7]));
//                    break;
//                }
//                case "update reservation": {
//                    reservationService.addOrUpdate(s[1], s[2], s[3], s[4], s[5]);
//                    break;
//                }
//                case "remove movie": {
//                    movieService.remove(s[1]);
//                    break;
//                }
//                case "remove client": {
//                    clientService.remove(s[1]);
//                    break;
//                }
//                case "remove reservation": {
//                    reservationService.remove(s[1]);
//                    break;
//                }
//                case "show movie":{
//                    for (Movie movie : movieService.getAll()) {
//                        System.out.println(movie);
//                    }
//                    break;
//                }
//                case "show client":{
//                    for (Client client : clientService.getAll()) {
//                        System.out.println(client);
//                    }
//                    break;}
//                case "show reservation": {
//                    for (Reservation reservation : reservationService.getAll()) {
//                        System.out.println(reservation);
//                    }
//                    break;
//                }
//                case "x":
//                    return;
//                default: {
//                    System.out.println("Invalid option!");
//                    break;
//                }
//            }
//        }
//    }
//}
