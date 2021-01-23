using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using dbsk7_2018.Models;

namespace dbsk7_2018.Controllers
{
    public class HomeController : Controller
    {
        // Skapar nya privata objekt
        private AlienModel am = new AlienModel();

        private FarlighetModel fm = new FarlighetModel();

        private SkeppModel sm = new SkeppModel();

        private TillverkarModel tm = new TillverkarModel();



        // Hämtar data från funktioner
        public IActionResult Index()
        {

            ViewBag.AlienTable = am.GetAllAliens();

            ViewBag.FarlighetTable = fm.GetAllFarlighet();

            ViewBag.SkeppTable = sm.GetAllSkepp();

            ViewBag.TillverkareTable = tm.GetAllTillverkare();

            return View();

        }
        // Söker efter ett namn i tabbellen ALIENS
        public IActionResult form(string name)
        {
            ViewBag.OregalienTable = am.SearchAliens(name);
            return View();
        }

        public ActionResult Delete(int id)
        {
            am.Delete(id);
            return RedirectToAction("form");
        }



        // Insert på tabellen Alien och Oregalien - Model: CustomersModel.cs
        public IActionResult InsertAlien(string namn, string IDKod, string ID_Kod, string farlighetKod, string RAS)
        {

       
            am.InsertAlien(namn, IDKod, ID_Kod, farlighetKod, RAS);

            am.insertOregalien(namn, IDKod, ID_Kod, farlighetKod, RAS);

            return RedirectToAction("Index");

        }

        // Updaterar tabellen skepp - Model: SkeppModel.cs
        public IActionResult UpdateSkepp(string ID, string SITTPLATSER, string TILLVERKATKOD)
        {

            sm.UpdateSkepp(ID, SITTPLATSER, TILLVERKATKOD);
            return RedirectToAction("Index");

        }


    }
}

