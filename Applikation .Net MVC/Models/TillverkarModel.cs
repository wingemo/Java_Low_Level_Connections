using System;
using MySql.Data.MySqlClient;
using System.Data;

namespace dbsk7_2018.Models
{
    public class TillverkarModel
    {
        private string connectionString = "Server=localhost;Database=a18phiwi;User ID=root;Pooling=false;SslMode=none;convert zero datetime=True;";

        public TillverkarModel()
        {
        }

        public DataTable GetAllTillverkare()
        {
            MySqlConnection dbcon = new MySqlConnection(connectionString);
            dbcon.Open();
            MySqlDataAdapter adapter = new MySqlDataAdapter("SELECT * FROM TILLVERKATKOD ;", dbcon);
            DataSet ds = new DataSet();
            adapter.Fill(ds, "result");
            DataTable skeppTable = ds.Tables["result"];
            dbcon.Close();

            return skeppTable;
        }

    }
}
