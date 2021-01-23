using System;
using MySql.Data.MySqlClient;
using System.Data;

namespace dbsk7_2018.Models
{
    public class FarlighetModel
    {
        private string connectionString = "Server=localhost;Database=a18phiwi;User ID=root;Pooling=false;SslMode=none;convert zero datetime=True;";

        public FarlighetModel()
        {
        }

        public DataTable GetAllFarlighet()
        {
            MySqlConnection dbcon = new MySqlConnection(connectionString);
            dbcon.Open();
            MySqlDataAdapter adapter = new MySqlDataAdapter("SELECT * FROM ALIENKOD ;", dbcon);
            DataSet ds = new DataSet();
            adapter.Fill(ds, "result");
            DataTable farlighetTable = ds.Tables["result"];
            dbcon.Close();

            return farlighetTable;
        }

    }
}
