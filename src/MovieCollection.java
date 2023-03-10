import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> castList= new ArrayList<String>();
    private ArrayList<String> genreList= new ArrayList<String>();

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);

        for(Movie movie: movies) {
            String cast = movie.getCast();
            String genre = movie.getGenres();
            while(cast.indexOf("|") != - 1) {
                castList.add(cast.substring(0, cast.indexOf("|")));
                cast = cast.substring(cast.indexOf("|")+ 1);
            }
            castList.add(cast);
            while(genre.indexOf("|") != - 1) {
                genreList.add(genre.substring(0, genre.indexOf("|")));
                genre = genre.substring(genre.indexOf("|")+ 1);
            }
            genreList.add(genre);
        }
        removeDuplicatesString(castList);
        removeDuplicatesString(genreList);
    }

    public void removeDuplicatesString(ArrayList<String> data){
        for (int i =0; i< data.size(); i++){
            for (int x=i+1; x< data.size(); x++){
                if (data.get(i).equals(data.get(x))){
                    data.remove(x);
                    x--;
                }
            }
        }
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast name: ");
        String userCastName= scanner.nextLine();

        userCastName = userCastName.toLowerCase();

        ArrayList<String> castResults = new ArrayList<String>();

        for(int i = 0;  i < castList.size(); i++) {
            String currentCastName = castList.get(i);
            if(currentCastName.toLowerCase().indexOf(userCastName) != -1) {
                castResults.add(currentCastName);
            }
        }
        insertionSortStr(castResults);

        for(int j = 0; j < castResults.size(); j++) {
            int num = j + 1;
            System.out.println("" + num + ". " + castResults.get(j));
        }

        System.out.println("Which cast would you like to learn more about?");
        System.out.print("Enter a number: ");

        int choice = scanner.nextInt();
        String selectedCast = castResults.get(choice - 1);
        selectedCast = selectedCast.toLowerCase();

        ArrayList<Movie> movieList = new ArrayList<Movie>();

        for(int k = 0; k < movies.size(); k++) {
            String currentMovieCast = movies.get(k).getCast().toLowerCase();
            if(currentMovieCast.indexOf(selectedCast) != -1) {
                movieList.add(movies.get(k));
            }
        }

        sortResults(movieList);

        for (int i = 0; i < movieList.size(); i++)
        {
            String title = movieList.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int movieChoice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = movieList.get(movieChoice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void insertionSortStr(ArrayList<String> data)
    {
        for (int j = 1; j < data.size(); j++)
        {
            int k = j;
            insertIntoSortedPartStr(k, data);
        }
    }

    private void insertIntoSortedPartStr(int k, ArrayList<String> data) {
        String v = data.get(k);
        while(k > 0 && v.compareTo(data.get(k-1)) < 0)
        {
            data.set(k, data.get(k-1));
            k--;
        }
        data.set(k,v);
    }
    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String userKeyWord = scanner.nextLine();

        userKeyWord = userKeyWord.toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();

        for(int i = 0; i < movies.size(); i++) {
            String movieKeyWord = movies.get(i).getKeywords().toLowerCase();
            if(movieKeyWord.indexOf(userKeyWord) != - 1) {
                results.add(movies.get(i));
            }
        }
        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }


    private void listGenres()
    {
        insertionSortStr(genreList);
        for (int i =0; i< genreList.size(); i++){
            System.out.println(i + 1+". "+ genreList.get(i));
        }
        System.out.println("Which movie genre would you like to see?");
        System.out.print("Enter number: ");
        int choice= scanner.nextInt();

        String genreSelected = genreList.get(choice-1);

        ArrayList<Movie> results= new ArrayList<Movie>();
        for (int i =0; i< movies.size(); i++){
            if (movies.get(i).getGenres().toLowerCase().indexOf(genreSelected.toLowerCase())!= -1){
                results.add(movies.get(i));

            }
        }
        sortResults(results);
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice2 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice2 - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listHighestRated()
    {
        ArrayList<Movie> results = new ArrayList<Movie>();
        results = movies;
        sortNumerically(results);
        for(int i = 50; i < results.size(); i++) {
            results.remove(i);
            i--;
        }
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title+ "| Rating: "+ results.get(i).getUserRating());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter a number: ");

        int choice2 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice2 - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortNumerically(ArrayList<Movie> data){
        for (int j = 1; j < data.size(); j++)
        {
            Movie temp = data.get(j);
            double tempRating = temp.getUserRating();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempRating > (data.get(possibleIndex - 1).getUserRating()))
            {
                data.set(possibleIndex, data.get(possibleIndex - 1));
                possibleIndex--;
            }
            data.set(possibleIndex, temp);
        }
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> results= new ArrayList<Movie>();
        results=movies;
        sortNumericallyRevenue(results);
        for (int i =50; i< results.size(); i++){
            results.remove(i);
            i--;
        }

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title+ "| Revenue: "+ results.get(i).getRevenue());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice2 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice2 - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }
    private void sortNumericallyRevenue(ArrayList<Movie> list){
        for (int j = 1; j < list.size(); j++)
        {
            Movie temp = list.get(j);
            double tempRating = temp.getRevenue();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempRating > (list.get(possibleIndex - 1).getRevenue()))
            {
                list.set(possibleIndex, list.get(possibleIndex - 1));
                possibleIndex--;
            }
            list.set(possibleIndex, temp);
        }
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}