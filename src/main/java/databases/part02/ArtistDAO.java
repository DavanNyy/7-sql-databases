package databases.part02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the Artist table in the Chinook database.
 */
public class ArtistDAO {

    /**
     * The connection string used to connect to the database. You MUST use this
     * string when connecting to the database using JDBC. In the unit tests, this
     * field will be set to a different value.
     */
    private final String connectionString;

    // Tallentaa tietokannan osoitteen.
    // Käytetään aina, kun muodostetaan yhteys tietokantaan.

    /**
     * Creates a new ArtistDAO that uses the specified connection string to connect
     * to the database. For example: "jdbc:sqlite:data/Chinook_Sqlite.sqlite"
     *
     * @param connectionString, see https://www.baeldung.com/java-jdbc-url-format
     */
    public ArtistDAO(String connectionString) {
        this.connectionString = connectionString;

        // Konstruktori tallentaa annetun yhteysosoitteen muuttujaan.
    }

    /**
     * Returns a list of all artists in the database. The list is ordered by artist
     * name. If there are no artists in the database, an empty list is returned.
     *
     * @return a list of all artists in the database.
     */
    public List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();

        /*
         * hint: see the class from part 1 for an example of how to connect to the
         * database and retrieve data from it. This time create new Artist objects
         * instead of printing the results to the console.
         *
         * Note that you must use the `connectionString` field in this class to connect
         * to the database. You can't "hard code" the connection string, as that would
         * make tests run against your actual database, which may have unexpected data.
         */
        String sql = "SELECT ArtistId, Name FROM Artist ORDER BY Name ASC";

        try (Connection connection = DriverManager.getConnection(connectionString);  // Muodostaa yhteyden tietokantaan.
                PreparedStatement preparedStatement = connection.prepareStatement(sql); // Valmistelee SQL-kyselyn suoritettavaksi.
                ResultSet resultSet = preparedStatement.executeQuery()) { //Suorittaa kyselyn.Tallentaa tulokset ResultSet-olioon.

            while (resultSet.next()) { //Siirtyy seuraavaan tietokannan riviin. Toistuu niin kauan kuin rivejä löytyy.
                long id = resultSet.getLong("ArtistId"); //Hakee artistin id:n.
                String name = resultSet.getString("Name"); //Hakee artistin nimen.

                artists.add(new Artist(id, name)); //Luo uuden Artist-olion. Lisää sen listaan.
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } //Jos tietokannassa tapahtuu virhe, tulostetaan virheen tiedot.

        return artists;
    }

    /**
     * Returns the artist with the specified id, or null if no artist exists with
     * that id.
     *
     * @param id the id of the artist to retrieve.
     * @return the artist with the specified id, or null if no artist exists with
     *         that id.
     */
    public Artist getArtistById(long id) { //Hakee yhden artistin id:n perusteella.
        /*
         * hint: use similar code to the getArtists() method above, but add a WHERE
         * clause to the SQL query to only retrieve the artist with the specified id.
         *
         * The id can be added to the SQL query using PreparedStatement's setLong()
         * method. For example: preparedStatement.setLong(1, id);
         *
         * You could also just call the getArtists() method above and iterate through
         * the results until you find the artist with the specified id. This is less
         * efficient, but it gets the job done and is easy to implement.
         *
         * Note that you must use the `connectionString` field in this class to connect
         * to the database. You can't "hard code" the connection string, as that would
         * make tests run against your actual database, which may have unexpected data.
         */
        for (Artist artist : getArtists()) {  //Hakee kaikki artistit.
            if (artist.getId() == id) {  //Verrataan artistin id:tä annettuun id:hen.
                return artist;
            }
        }
        return null;
    }
}
