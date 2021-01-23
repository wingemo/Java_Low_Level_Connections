using System;
using MySql.Data.MySqlClient;
using System.Data;

namespace dbsk7_2018.Models
{
    public class SkeppModel
    {
        private string connectionString = "Server=localhost;Database=a18phiwi;User ID=root;Pooling=false;SslMode=none;convert zero datetime=True;";

        public SkeppModel()
        {
        }

        // Hämtar alla skepp
        public DataTable GetAllSkepp()
        {
            MySqlConnection dbcon = new MySqlConnection(connectionString);
            dbcon.Open();
            MySqlDataAdapter adapter = new MySqlDataAdapter("SELECT * FROM SKEPP ;", dbcon);
            DataSet ds = new DataSet();
            adapter.Fill(ds, "result");
            DataTable skeppTable = ds.Tables["result"];
            dbcon.Close();

            return skeppTable;
        }

        // Updaterar Skepp    
        public void UpdateSkepp(string ID, string SITTPLATSER, string TILLVERKATKOD)
        {
            MySqlConnection dbcon = new MySqlConnection(connectionString);
            dbcon.Open();
            string deleteString = "UPDATE SKEPP SET SITTPLATSER =  @SITTPLATSER, TILLVERKATKOD = @TILLVERKATKOD WHERE  ID = @ID;";
            MySqlCommand sqlCmd = new MySqlCommand(deleteString, dbcon);
            sqlCmd.Parameters.AddWithValue("@SITTPLATSER", SITTPLATSER);
            sqlCmd.Parameters.AddWithValue("@TILLVERKATKOD", TILLVERKATKOD);
            sqlCmd.Parameters.AddWithValue("@ID", ID);
            int rows = sqlCmd.ExecuteNonQuery();
            dbcon.Close();


        }

    }
}
