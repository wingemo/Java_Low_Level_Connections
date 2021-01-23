using System;
using MySql.Data.MySqlClient;
using System.Data;

namespace dbsk7_2018.Models
{
    public class AlienModel
    {

        private string connectionString = "Server=localhost;Database=a18phiwi;User ID=root;Pooling=false;SslMode=none;convert zero datetime=True;";

        public AlienModel()
        {
        }

        public DataTable GetAllAliens()
        {
            MySqlConnection dbcon = new MySqlConnection(connectionString);
            dbcon.Open();
            MySqlDataAdapter adapter = new MySqlDataAdapter("SELECT ALIENS.NAMN, ALIENS.IDKod,  Oregalien.ID_Kod, ALIENS.farlighetKod, ALIENS.RAS FROM ALIENS, Oregalien WHERE  Oregalien.IDkod = ALIENS.IDkod  ;", dbcon);
            DataSet ds = new DataSet();
            adapter.Fill(ds, "result");
            DataTable alienTable = ds.Tables["result"];
            dbcon.Close();

            return alienTable;
        }

        public void InsertAlien(string namn, string IDKod, string ID_Kod, string farlighetKod, string RAS)
        {
            MySqlConnection dbcon = new MySqlConnection(connectionString);
            dbcon.Open();
            string deleteString = "INSERT INTO ALIENS(NAMN,IDkod,farlighetKod,RAS) VALUES(@NAMN,@IDkod,@farlighetKod, @RAS);";
            MySqlCommand sqlCmd = new MySqlCommand(deleteString, dbcon);
            sqlCmd.Parameters.AddWithValue("@NAMN", namn);
            sqlCmd.Parameters.AddWithValue("@IDKod", IDKod);
            sqlCmd.Parameters.AddWithValue("@farlighetKod", farlighetKod);
            sqlCmd.Parameters.AddWithValue("@RAS", RAS);
            sqlCmd.Parameters.AddWithValue("@ID_Kod", ID_Kod);
            int rows = sqlCmd.ExecuteNonQuery();
            dbcon.Close();


        }

        public void insertOregalien(string namn, string IDKod, string ID_Kod, string farlighetKod, string RAS)
        {
            MySqlConnection dbcon = new MySqlConnection(connectionString);
            dbcon.Open();
            string insertOregalien = "INSERT INTO Oregalien(NAMN,IDkod, ID_Kod) VALUES(@NAMN,@IDkod, @ID_Kod);";
            MySqlCommand sqlCmd = new MySqlCommand(insertOregalien, dbcon);
            sqlCmd.Parameters.AddWithValue("@NAMN", namn);
            sqlCmd.Parameters.AddWithValue("@IDKod", IDKod);
            sqlCmd.Parameters.AddWithValue("@ID_Kod", ID_Kod);
            int rows = sqlCmd.ExecuteNonQuery();
            dbcon.Close();


        }
        public DataTable SearchAliens(string name)
        {
            MySqlConnection dbcon = new MySqlConnection(connectionString);
            dbcon.Open();
            MySqlDataAdapter adapter = new MySqlDataAdapter("SELECT * FROM Oregalien WHERE NAMN LIKE @NAME;", dbcon);
            adapter.SelectCommand.Parameters.AddWithValue("@NAME", name);
            DataSet ds = new DataSet();
            adapter.Fill(ds, "result");
            DataTable AlienTable = ds.Tables["result"];
            dbcon.Close();
            return AlienTable;
        }


        public void Delete(int id)
        {
            MySqlConnection dbcon = new MySqlConnection(connectionString);
            dbcon.Open();
            string deleteString = "DELETE FROM Oregalien WHERE IDKod=@ID;";
            MySqlCommand sqlCmd = new MySqlCommand(deleteString, dbcon);
            sqlCmd.Parameters.AddWithValue("@ID", id);
            int rows = sqlCmd.ExecuteNonQuery();
            dbcon.Close();
        }



    }
}
