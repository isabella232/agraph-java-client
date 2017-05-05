/******************************************************************************
** See the file LICENSE for the full license governing this code.
******************************************************************************/

package com.franz.agraph.repository;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openrdf.model.IRI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.rio.ntriples.NTriplesUtil;

/**
 * The class of freetext index configurations.
 * 
 * An index configuration can be customized and then used to create a new freetext index.
 * 
 * See documentation for 
 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
 * 
 * @see #newInstance()
 * @see AGRepositoryConnection#createFreetextIndex(String, AGFreetextIndexConfig)
 */
public class AGFreetextIndexConfig {

	public static final String PREDICATES = "predicates";
	public static final String INDEX_LITERALS = "indexLiterals";
	public static final String INDEX_LITERAL_TYPES = "indexLiteralTypes";
	public static final String INDEX_RESOURCES = "indexResources";
	public static final String INDEX_FIELDS = "indexFields";
	public static final String MINIMUM_WORD_SIZE = "minimumWordSize";
	public static final String STOP_WORDS = "stopWords";
	public static final String WORD_FILTERS = "wordFilters";
	public static final String INNER_CHARS = "innerChars";
	public static final String BORDER_CHARS = "borderChars";
	public static final String TOKENIZER = "tokenizer";
	
	private static final ValueFactory vf = new ValueFactoryImpl(); 

	private List<IRI> predicates;
	private boolean indexLiterals;
	private List<String> indexLiteralTypes;
	private String indexResources;
	private List<String> indexFields;
	private int minimumWordSize;
	private List<String> stopWords;
	private List<String> wordFilters;
	private List<String> innerChars;
	private List<String> borderChars;
	private String tokenizer;

	/**
	 * Returns a new instance having the default index configuration.
	 * 
	 * The index configuration can be customized and then used
	 * to create a new freetext index.
	 * 
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @return AGFreetextIndexConfig  the created config
	 * @see AGRepositoryConnection#createFreetextIndex(String, AGFreetextIndexConfig)
	 */
	public static AGFreetextIndexConfig newInstance() {
		return new AGFreetextIndexConfig();
	}
	
	private AGFreetextIndexConfig() {	
		predicates = new ArrayList<IRI>();
		indexLiterals = true;
		indexLiteralTypes = new ArrayList<String>();
		indexResources = "false";
		indexFields = new ArrayList<String>();
		indexFields.add("object");
		minimumWordSize = 3;
		stopWords = new ArrayList<String>();
		wordFilters = new ArrayList<String>();
		innerChars = new ArrayList<String>();
		borderChars = new ArrayList<String>();
		tokenizer = "default";
	}
	
	AGFreetextIndexConfig(JSONObject config) {
		predicates = initPredicates(config);
		indexLiterals = initIndexLiterals(config);
		indexLiteralTypes = getJSONArrayAsListString(config, INDEX_LITERAL_TYPES);
		indexResources = config.optString(INDEX_RESOURCES);
		indexFields = getJSONArrayAsListString(config, INDEX_FIELDS);
		minimumWordSize = config.optInt(MINIMUM_WORD_SIZE);
		stopWords = getJSONArrayAsListString(config, STOP_WORDS);
		wordFilters = getJSONArrayAsListString(config, WORD_FILTERS);
		innerChars = getJSONArrayAsListString(config, INNER_CHARS);
		borderChars = getJSONArrayAsListString(config, BORDER_CHARS);
		tokenizer = config.optString(TOKENIZER);
	}
	
	private boolean initIndexLiterals(JSONObject config) {
		boolean bool;
		try {
			// Can't use optBoolean here, it defaults to false
			bool = config.getBoolean(INDEX_LITERALS);
		} catch (JSONException e) {
			// TODO: perhaps log this if it happens.
			bool = true;
		}
		return bool;
	}
	
	private List<IRI> initPredicates(JSONObject config) {
		List<IRI> predList = new ArrayList<IRI>();
		JSONArray preds = config.optJSONArray(PREDICATES);
		if (preds!=null) {
			for (int i = 0; i < preds.length(); i++) {
				String uri_nt = preds.optString(i);
				IRI uri = NTriplesUtil.parseURI(uri_nt, vf);
				predList.add(uri);
			}
		}
		return predList;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @return List  the Predicates being indexed
	 */
	public List<IRI> getPredicates() {
		return predicates;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @return boolean  the current IndexLiterals setting
	 */
	public boolean getIndexLiterals() {
		return indexLiterals;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @param bool  true if Literals should be indexed, false if not
	 */
	public void setIndexLiterals(boolean bool) {
		indexLiterals = bool;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @return  the current IndexLiteralTypes setting
	 */
	public List<String> getIndexLiteralTypes() {
		return indexLiteralTypes;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @return String  the current indexResources setting
	 */
	public String getIndexResources() {
		return indexResources;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @param str  A string indicating how to index resources
	 */
	public void setIndexResources(String str) {
		//TODO: validity check
		indexResources = str;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @return List  the fields (s, p, o, g) being indexed
	 */
	public List<String> getIndexFields() {
		return indexFields;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @return int  the minimum word size associated with this config
	 */
	public int getMinimumWordSize() {
		return minimumWordSize;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @param size  the minimum word size to be set
	 */
	public void setMinimumWordSize(int size) {
		minimumWordSize = size;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @return List  the stop words associated with this config
	 */
	public List<String> getStopWords() {
		return stopWords;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @return List  the word filters associated with this config
	 */
	public List<String> getWordFilters() {
		return wordFilters;
	}
	
	public List<String> getInnerChars() {
		return innerChars;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @return List  the border characters associated with this config
	 */
	public List<String> getBorderChars() {
		return borderChars;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @return String  name of the tokenizer
	 */
	public String getTokenizer() {
		return tokenizer;
	}
	
	/**
	 * See documentation for 
	 * <a href="http://www.franz.com/agraph/support/documentation/current/http-protocol.html#put-freetext-index">freetext index parameters</a>.
	 * 
	 * @param tokenizerName  name of a valid tokenizer
	 */
	public void setTokenizer(String tokenizerName) {
		tokenizer = tokenizerName;
	}
	
	private List<String> getJSONArrayAsListString(JSONObject config, String key) {
		List<String> list = new ArrayList<String>();
			JSONArray array = config.optJSONArray(key);
			if (array!=null) {
				for (int i = 0; i < array.length(); i++) {
					list.add(array.optString(i));
				}
			}
		return list;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<AGFreetextIndexConfig predicates: ").append(predicates);
		sb.append(", indexLiterals: ").append(indexLiterals);
		sb.append(", indexLiteralTypes: ").append(indexLiteralTypes);
		sb.append(", indexResources: ").append(indexResources);
		sb.append(", indexFields: ").append(indexFields);
		sb.append(", minimumWordSize: ").append(minimumWordSize);
		sb.append(", stopWords: ").append(stopWords.size());
		sb.append(", wordFilters: ").append(wordFilters);
		sb.append(", innerChars: ").append(innerChars);
		sb.append(", borderChars: ").append(borderChars);
		sb.append(", tokenizer: ").append(tokenizer);
		sb.append(">");
		return sb.toString();
	}
}
