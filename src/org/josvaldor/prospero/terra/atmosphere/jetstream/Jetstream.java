package org.josvaldor.prospero.terra.atmosphere.jetstream;

import java.io.IOException;
import ucar.nc2.Variable;
import ucar.nc2.NetcdfFile;
import ucar.ma2.ArrayFloat;
import ucar.ma2.ArrayInt;
import ucar.ma2.ArrayShort;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFileWriter;


public class Jetstream {
	public static int longitudeCount = 0;
    public static int latitudeCount = 0;
    public static int levelCount = 0;
    public static int timeCount = 0;
    public static int uCount = 0;
    public static int vCount = 0;
    public static String startDate = null;
    public static String fileName = "./data/jetstream/default.nc";
    public static NetcdfFileWriter netCDFFile;

    public static void main(String[] args) {
        processFile();
    }

    public static void processFile() {
        String filename = "./data/jetstream/netcdf.nc";
        NetcdfFile dataFile = null;
        try {
            dataFile = NetcdfFile.open(filename, null);
            Variable latVar = dataFile.findVariable("latitude");
            Variable lonVar = dataFile.findVariable("longitude");
            Variable level = dataFile.findVariable("level");
            Variable time = dataFile.findVariable("time");
            Variable uVar = dataFile.findVariable("u");
            Variable vVar = dataFile.findVariable("v");
            longitudeCount = (int) lonVar.getSize();
            latitudeCount = (int) latVar.getSize();
            levelCount = (int) level.getSize();
            timeCount = (int) time.getSize();
            uCount = (int) uVar.getSize();
            vCount = (int) uVar.getSize();
            System.out.println(longitudeCount);
            System.out.println(latitudeCount);
            System.out.println(levelCount);
            System.out.println(timeCount);
            System.out.println(uCount);
            System.out.println(vCount);
            ArrayFloat.D1 latArray = (ArrayFloat.D1) latVar.read();
            ArrayFloat.D1 lonArray = (ArrayFloat.D1) lonVar.read();
            ArrayInt.D1 levelArray = (ArrayInt.D1) level.read();
            ArrayInt.D1 timeArray = (ArrayInt.D1) time.read();
            ArrayShort.D4 uArray = (ArrayShort.D4) uVar.read();
            ArrayShort.D4 vArray = (ArrayShort.D4) vVar.read();
            fileName = (fileName != null) ? fileName : "./data/default.nc";
            try {
                netCDFFile = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf4, fileName);
                //Dimension
                Dimension latitudeDimension = netCDFFile.addDimension(null, "latitude", (int) latArray.getSize());
                Dimension longitudeDimension = netCDFFile.addDimension(null, "longitude", (int) lonArray.getSize());
                Dimension pressureDimension = netCDFFile.addDimension(null, "level", (int) levelArray.getSize());
                Dimension timeDimension = netCDFFile.addUnlimitedDimension("time");
                Dimension speedDimension = netCDFFile.addDimension(null, "speed", (int) uArray.getSize());

                Variable longitudeVar = netCDFFile.addVariable(null, "longitude", DataType.FLOAT, "longitude");
                Variable latitudeVar = netCDFFile.addVariable(null, "latitude", DataType.FLOAT, "latitude");
                Variable pressureVar = netCDFFile.addVariable(null, "level", DataType.INT, "level");
                Variable timeVar = netCDFFile.addVariable(null, "time", DataType.INT, "time");
                Variable speedVar = netCDFFile.addVariable(null, "speed", DataType.FLOAT, "time level latitude longitude");

                longitudeVar.addAttribute(new Attribute("standard_name", "longitude"));
                longitudeVar.addAttribute(new Attribute("long_name", "longitude"));
                longitudeVar.addAttribute(new Attribute("units", "degrees_east"));
                longitudeVar.addAttribute(new Attribute("axis", "X"));
                latitudeVar.addAttribute(new Attribute("standard_name", "latitude"));
                latitudeVar.addAttribute(new Attribute("long_name", "latitude"));
                latitudeVar.addAttribute(new Attribute("units", "degrees_north"));
                latitudeVar.addAttribute(new Attribute("axis", "Y"));
                pressureVar.addAttribute(new Attribute("standard_name", "air_pressure"));
                pressureVar.addAttribute(new Attribute("long_name", "pressure"));
                pressureVar.addAttribute(new Attribute("units", "Pa"));
                pressureVar.addAttribute(new Attribute("positive", "down"));
                pressureVar.addAttribute(new Attribute("axis", "Z"));
                timeVar.addAttribute(new Attribute("standard_name", "time"));
                timeVar.addAttribute(new Attribute("calendar", "proleptic_gregorian"));
                timeVar.addAttribute(new Attribute("axis", "T"));
                speedVar.addAttribute(new Attribute("standard_name", "speed"));
                speedVar.addAttribute(new Attribute("long_name", "intensity"));
                speedVar.addAttribute(new Attribute("code", "138"));
                speedVar.addAttribute(new Attribute("table", "128"));
                speedVar.addAttribute(new Attribute("grid_type", "gaussian"));
                System.out.println("create netcdf file");
                netCDFFile.create();
                ArrayFloat.D1 latitudeData = latArray;
                ArrayFloat.D1 longitudeData = lonArray; //new ArrayDouble.D1(longitudeDimension.getLength());
                ArrayInt.D1 pressureData = levelArray; //new ArrayDouble.D1(pressureDimension.getLength());
                ArrayInt.D1 timeData = timeArray; //new ArrayDouble.D1(map.matrix[0][0][0].length);
                ArrayFloat.D4 speedData = new ArrayFloat.D4(timeCount,levelCount,latitudeCount,longitudeCount);//new ArrayFloat.D4(timeDimension.getLength(), pressureDimension.getLength(), latitudeDimension.getLength(), longitudeDimension.getLength());
                ArrayShort.D4 vData = vArray;
                ArrayShort.D4 uData = uArray;
                float speed = 0;
                float u = 0;
                float v = 0;
                float latitude = 0;
                float longitude = 0;
                for (int l = 0; l < timeCount; l++) {
                    for (int k = 0; k < levelCount; k++) {
                        for (int j = 0; j < latitudeCount; j++) {
                            latitude = latitudeData.get(j);
                            for (int i = 0; i < longitudeCount; i++) {
                                longitude = longitudeData.get(i);
//                                speed = speedData.get(l, k, j, i);
                                u = uData.get(l, k, j, i)/1000;
                                v = vData.get(l, k, j, i)/1000;
//                                System.out.println("u"+u);
//                                System.out.println("v"+v);
                                speed = (float) (Math.pow(u,2) + Math.pow(v, 2));
//                                speedData.set(l, k, j, i, 0);
//                                System.out.println(speed);
                                if (speed > 177) 
                                {
//                                    System.out.println(speed);
//                                    System.out.println((short)speed);
                                    speedData.set(l, k, j, i, speed);
                                      if (latitude > -30 & latitude < 30) {
                                        speedData.set(l, k, j, i, (short) 0);
                                    }
//                                    
                                }
//////                                    speedData.set(l,k,j,i,(short)speed);
//                              
//                                } else {
//                                    speedData.set(l, k, j, i, (short) 0);
//                                }
                            }
                        }
                    }
                }

                System.out.println("Writing data");

                //Write data to file
                netCDFFile.write(longitudeVar, longitudeData);
                netCDFFile.write(latitudeVar, latitudeData);
                netCDFFile.write(pressureVar, pressureData);
                netCDFFile.write(timeVar, timeData);
                netCDFFile.write(speedVar, speedData);
                netCDFFile.close();
            } catch (IOException | InvalidRangeException e) {
                e.printStackTrace();

            } finally {
                System.out.println("finally");
                if (null != netCDFFile) {
                    try {
                        netCDFFile.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
       

        } finally {
            if (dataFile != null) {
                try {
                    dataFile.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

    }
}
