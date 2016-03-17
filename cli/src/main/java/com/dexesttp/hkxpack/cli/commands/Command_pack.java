package com.dexesttp.hkxpack.cli.commands;

import java.io.File;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkxwriter.HKXWriter;
import com.dexesttp.hkxpack.resources.DisplayProperties;
import com.dexesttp.hkxpack.tagreader.TagXMLReader;

public class Command_pack extends Command_IO {

	@Override
	protected Runnable getThreadLambda(String inputFileName, String outputFileName,
			HKXDescriptorFactory descriptorFactory, HKXEnumResolver enumResolver) {
		return () -> {
			try {
				// Read file
				File inFile = new File(inputFileName);
				TagXMLReader reader = new TagXMLReader(inFile, descriptorFactory);
				HKXFile file = reader.read();
				
				File outFile = new File(outputFileName);
				outFile.createNewFile();
				HKXWriter writer = new HKXWriter(outFile, enumResolver);
				writer.write(file);
			} catch (Exception e) {
				System.out.println("Error reading file : " + inputFileName);
				if(DisplayProperties.displayDebugInfo)
					e.printStackTrace();
				else
					System.err.println(e.getMessage());
			}
		};
	}

	@Override
	protected String extractFileName(String ogName) {
		return ogName.substring(0, ogName.lastIndexOf(".")) + ".hkx";
	}

	@Override
	protected String[] getFileExtensions() {
		return new String[] {".xml"};
	}
}
