package repository.streams;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import repository.interfaces.iStream;

public class JSONFileStream<E> implements iStream<E> {
	private ObjectMapper objectMapper;
	private String filePath;
	private final String DB_PREFIX = "DB/";
	
	public JSONFileStream(String filePath) {
		this.filePath = filePath;
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public void saveAll(List<E> entities){
		try {
			checkIfFileExists();
			File file = new File(DB_PREFIX + filePath);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(objectMapper.writeValueAsString(entities));

            bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<E> readAll() {
		try {
			System.out.println(readTextFromFile());
			return objectMapper.readValue(readTextFromFile(), new TypeReference<List<E>>() {});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void appendToFile(E entity) {
		List<E> allEntities = readAll();
		allEntities.add(entity);
		saveAll(allEntities);
		
	}
	
	private boolean checkIfFileExists() {
		Boolean retval = true;
		
		File directory = new File(DB_PREFIX);
		if (! directory.exists()){
	        directory.mkdir();
	        retval = false;
	    }
		try {
			File file = new File(DB_PREFIX + filePath);
	        if (!file.exists()) {
	            file.createNewFile();
	            retval = false;
	        }
	        return retval;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String readTextFromFile() {
		try {
			if(!checkIfFileExists() || (new File(DB_PREFIX + filePath)).length() == 0) return "[]";
				
			BufferedReader br = new BufferedReader(new FileReader(DB_PREFIX + filePath));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    br.close();
		    
		    return sb.toString();	
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
