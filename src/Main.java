
/*
 * XMLFormatter - An Advanced xml reformatter with proper indentation feature
 * Copyright 2024, developer-krushna
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of developer-krushna nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 *     Please contact Krushna by email mt.modder.hub@gmail.com if you need
 *     additional information or have any questions
 */
 
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
/*
 Author @developer-krushna
 */
public class Main {
    public static void main(String[] args) {
        // Initialize the formatting class
        XMLFormatter xmlFormatter = new XMLFormatter();

        // Set formatting options (optional)
	    	// xmlFormatter.setFormattingOption(1);
		
        xmlFormatter.setIndentation(4); // Set indentation level

        // Load XML content from file
        String filePath = "/storage/emulated/0/MyInvalidXmlFile.xml"; // Update with the actual file path
        String xmlContent = loadXmlFromFile(filePath);
		
		    //check the xml text valid or not
		    if(xmlFormatter.isValidXml(xmlContent)){
			
        if (xmlContent != null) {
            // Format the XML content
            String formattedXml = xmlFormatter.format(xmlContent);

            // Output the formatted XML
            System.out.println("Formatted XML:");

            // Save the formatted XML to a new file
            String outputFile = "/storage/emulated/0/MyReformattedXmlFile.xml"; // Update with the desired output file path
            saveXmlToFile(formattedXml, outputFile);
            System.out.println("Formatted XML saved to: " + outputFile);
        } else {
            System.out.println("Failed to load XML from file: " + filePath);
        }
		
		}else {
			System.out.println("Invalid xml text" + filePath);
		}
}

    private static String loadXmlFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void saveXmlToFile(String xmlContent, String filePath) {
        try {
            Files.write(Paths.get(filePath), xmlContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
