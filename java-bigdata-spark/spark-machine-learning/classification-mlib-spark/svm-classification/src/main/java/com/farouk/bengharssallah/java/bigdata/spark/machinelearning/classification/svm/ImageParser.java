package com.farouk.bengharssallah.java.bigdata.spark.machinelearning.classification.svm;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;

import javax.imageio.ImageIO;



			/**
			 * <p> {@link ImageParser} reads images belonging to a sample Class e.g. Male/Female . Then, it writes the images to 
			 * Vector format into a text file. These text files will be used by Apache Spark for Linear SVM analysis.<p>
			 ***/

public class ImageParser {
	

	             /** the pixel to which the image will be converted **/
	    private String pixel;
	    
	           /**  folder containing the images**/
	    private String inputFolder;
	    
	           /** folder which will contain generated files **/
	    private String outputFolder;
	    
	           /** category is the Class of the image, Class = 1 for Male/ 0 for Female **/
	    private String category;
	
	

				   public ImageParser(String pixel, String inputFolder, String outputFolder, String category) {
				                  this.pixel = pixel;
				                  this.inputFolder = inputFolder;
				                  this.outputFolder = outputFolder;
				                  this.category = category;
		                      }



				   /** 
					 * <p>This method will convert the provided to the Gray format.</p>
					 *  @param image {@link BufferedImage} image to be converted
					 ***/
	
			public void toGray(BufferedImage image) {
							int width = image.getWidth();
							int height = image.getHeight();
							for(int i=0; i<height; i++){
							              for(int j=0; j<width; j++){
							                       Color c = new Color(image.getRGB(j, i));
							                       int red = (int)(c.getRed() * 0.21);
							                       int green = (int)(c.getGreen() * 0.72);
							                       int blue = (int)(c.getBlue() *0.07);
							                       int sum = red + green + blue;
							                       Color newColor = new Color(sum,sum,sum);
							                       image.setRGB(j,i,newColor.getRGB());
							                     }		
							               }
					        }
			
			
			
					   /** 
						 * <p>This method will create a copy of the provided image with the given width and height.</p>
						 *  @param originalImage {@link BufferedImage} image to be copied
						 *  @param scaledWidth {@link Integer} image to be converted
						 *  @param scaledHeight {@link Integer} image to be converted
						 *  @param preserveAlpha {@link Boolean} preserve Alpha Property of the image
						 ***/

				public BufferedImage createResizedCopy(BufferedImage originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha){
										int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
										BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
										Graphics2D graphic = scaledBI.createGraphics();
										if (preserveAlpha) {
											             graphic.setComposite(AlphaComposite.Src);
										                         }
										graphic.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
										graphic.dispose();
										return scaledBI;
				              }
				
				
				
				
				
				/** 
				 * <p>This method will convert the images in the suitable format to be trained as a 
				 * model for the SVM Machine learning algorithm.</p>
				 ***/

		public void parse(){
		
					     // the pixel to which the image will be converted 
					String[] piexels = pixel.split("×");
					
					int scaledWidth = Integer.parseInt(piexels[0]);
					int scaledHeight = Integer.parseInt(piexels[1]);
					
					ArrayList<String> paths = new ArrayList<String>();
					
					
					     // Traverse All the files inside the Folder and sub folder.  imagePath is the path of the folder containing the images
					try {
						Files.walkFileTree(Paths.get(inputFolder.toString()), new SimpleFileVisitor<Path>() {
						@Override
						public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
												//Files.delete(file);
												paths.add(file.toFile().getAbsolutePath());
												return FileVisitResult.CONTINUE;
						        }
						
						@Override
						public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
												//Files.delete(dir);
												return FileVisitResult.CONTINUE;
						              }
						
						   });
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					
					for(String file : paths){
					
									String extension = "";
									int extIndex = file.indexOf('.');
									extension = file.substring(extIndex + 1);
									File input = new File(file.toString());
									         // creating a copy of image
									BufferedImage image = null;
									try {
										image = createResizedCopy(ImageIO.read(input), scaledWidth, scaledHeight, Boolean.TRUE);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									            // converting image to the Gray format
									toGray(image);
									File output = new File(file.toString());
									try {
										ImageIO.write(image, extension, output);
									} catch (IOException e) {
										e.printStackTrace();
									}
					       }
					
					
					try{
						
									       // category is the Class of the image, Class = 1 for Male/ 0 for Female. Vector will be written into a text file
									try(PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFolder + "/input-" + category + ".csv", true)))) {
										
														for(String file : paths){
																		File file1 = new File(file.toString());
																		BufferedImage img = ImageIO.read(file1);
																		if (img == null) 
																			         continue;
																		Raster raster = img.getData();
																		int width = raster.getWidth();
																		int height = raster.getHeight();
																		printWriter.print(category);
																		printWriter.print("," + file1.getName() + ",");
																		for (int x=0; x<width; x++){
																		             for(int y=0; y<height; y++){
																		            	 printWriter.print(raster.getSample(x,y,0)+" ");
																		                           }
																		             printWriter.print(" ");
																		             }
																		printWriter.println("");
														          }
									
									                 }
									catch (Exception e) {
									                               e.printStackTrace();    
									                     }
									
									
									          }
					catch(Exception e){
						             e.printStackTrace();
					                 }
					      }
		
		}