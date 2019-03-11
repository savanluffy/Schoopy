using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schoopy
{

    public enum Department { IT, BUILDINGCONSTRUCTION, CIVILENGINEERING, NETWORKTECHNOLOGY, MEDIATECHNOLOGY,NONE }
    public class Room
    {
        public string roomNr { get; set; }
        public string roomDescription { get; set; }
        public Department department { get; set; }
        public double[] roomCoordinates { get; set; }

        public override string ToString()
        {
            return this.roomDescription;
        }
    }
}
