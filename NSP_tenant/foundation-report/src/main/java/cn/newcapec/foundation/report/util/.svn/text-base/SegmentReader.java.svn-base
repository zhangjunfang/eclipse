package cn.newcapec.foundation.report.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 语句分段读取器，它能根据关键词分段读出中间的内容。</p>
 * 你可以设置多个分段关键词让分析器来处理，例如：
 * <pre>
 * I am a boy, but my sister is a good girl!</br>
 * 你可以设置关键词对来解析内容：[I,boy],[but ,girl!]
 * 经过这两个关键词分析后，会返回以下结果：</br>
 * [ am a],[ my sister is a good ]
 * </pre>
 * @author shikeying
 * @date 2013-7-17
 *
 */
public class SegmentReader {

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 定义组件的选项开关
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/* 关键词是否大小写敏感，默认否 */
	private boolean keyCaseSensitive = false;
	
	/* 返回结果中是否包含关键词，默认不包含 */
	private boolean contentIncludeKey = false;
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 定义组件内部私有变量
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	/* 最终分析过后的字符串内容 */
	private final StringBuilder totalResult = new StringBuilder(512);
	
	/* 解析后的最终结果，可以通过关键词对来查找对应结果 */
//	private final Map<KeyExpression, StringBuilder> result = new HashMap<KeyExpression, StringBuilder>(2);
	private final Map<KeyExpression, List<StringBuilder>> result2 = new HashMap<KeyExpression, List<StringBuilder>>(2);
	
	/* 用户添加的所有关键词信息 */
	private final List<KeyExpression> keys = new ArrayList<KeyExpression>(2);
	
	/* 开始关键词与结束关键词对应，便于快速查找 */
	private final Map<String, String> startEndKeys = new HashMap<String, String>(2);
	
	private int maxStartKeyLength, maxEndKeyLength = 0;
	
	/* 当前已经扫描过入栈字符 */
	private LinkedList<Character> scanStack = new LinkedList<Character>();
	
	/* 已入栈的关键词 */
	private LinkedList<String> existKeysStack = new LinkedList<String>();
	
	/* 关键词排除的字符集合，即有些关键词不能出现在特定的字符中 */
	/* 例如SQL语句中关键词分号不能在''中，有些字符串中也会包含关键词 */
	/* 这些被排除的字符都是成对出现的。 */
	public static final Set<Character> keyExcludedCharSet = new HashSet<Character>();
	
	static {
		keyExcludedCharSet.add('\'');
	}
	
	private LinkedList<Character> keyExcludedCharStack = new LinkedList<Character>();
	
	/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 *  组件暴露方法调用
	 * 
	 ** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/**
	 * 读取给定的语句，组件准备分析
	 * @param sentence
	 * @return 返回分析过的最终完整的语句内容
	 */
	public String read(String sentence){
		assert (sentence != null && !sentence.equals(""));
		if(keys.size() == 0)
			throw new IllegalStateException("keys is required.");
		
		getMaxKeyLength();
		for(KeyExpression ke : keys){
//			result.put(ke, new StringBuilder(512));
			result2.put(ke, new ArrayList<StringBuilder>());
			startEndKeys.put(ke.getStartKey(), ke.getEndKey());
		}
		for(char c : (keyCaseSensitive ? sentence.toCharArray() : sentence.toLowerCase().toCharArray())){
			readOneCharacter(c, false);
		}
		
		/* 处理最后一个读取字符问题 */
		readOneCharacter(scanStack.peekLast(), true);
		
		return totalResult.toString();
	}
	
	/**
	 * 返回关键词对应的处理结果，
	 * @param startKey 开始关键词
	 * @param endKey 结束关键词
	 * @return
	 */
	public String getSolvedContent(String startKey, String endKey){
		KeyExpression ke = new KeyExpression(startKey, endKey);
//		StringBuilder sb = this.result.get(ke);
		List<StringBuilder> sb = this.result2.get(ke);
		return sb == null ? null : sb.size() == 0 ? null : sb.get(0).toString();
	}
	
	/**
	 * 根据关键词，返回该关键词包含的分析结果列表。</br>
	 * 输入的关键词不能嵌套，是平行关系。如下示例：
	 * <pre>
	 * select ... from ... (select * from ...).
	 * select是开始，from是结束关键词
	 * </pre>
	 * @param startKey
	 * @param endKey
	 * @return
	 */
	public List<StringBuilder> getSolvedList(String startKey, String endKey){
		return this.result2.get(new KeyExpression(startKey, endKey));
	}
	
	/**
	 * 关键词分析后的结果处理模式: 存储
	 */
	public static final int RESULT_MODE_STORE     = 1;

	/**
	 * 关键词分析后的结果处理模式: 删除
	 */
	public static final int RESULT_MODE_REMOVE    = 2;

	/**
	 * 关键词分析后的结果处理模式: 替换内容
	 */
	public static final int RESULT_MODE_REPLACE   = 3;

	/**
	 * 关键词分析后的结果处理模式: 通过回调接口来处理
	 */
	public static final int RESULT_MODE_CALLBACK = 4;

	/**
	 * 添加默认的关键词，系统默认会返回解析的结果
	 * @param startKey 开始关键词
	 * @param endKey 结束关键词，可以没有
	 */
	public void addKey(String startKey, String endKey){
		addKey(startKey, endKey, RESULT_MODE_STORE);
	}
	
	/**
	 * 添加关键词，删除行为。即：关键词之间的内容被删除
	 * @param startKey
	 * @param endKey
	 */
	public void addRemoveKey(String startKey, String endKey){
		addKey(startKey, endKey, RESULT_MODE_REMOVE);
	}
	
	/**
	 * 添加关键词，替换行为。即：关键词之间内容会被替换
	 * @param startKey
	 * @param endKey
	 * @param replace 要替换的内容
	 */
	public void addReplaceKey(String startKey, String endKey, String replace){
		addKey(startKey, endKey, RESULT_MODE_REPLACE, replace);
	}
	
	/**
	 * 添加关键词，回调行为。即：关键词之间内容会被回调接口继续调用，最终结果仍会返回
	 * @param startKey
	 * @param endKey
	 * @param callback 用户自定义实现的回调实现
	 */
	public void addCallbackKey(String startKey, String endKey, CallBack callback){
		addKey(startKey, endKey, RESULT_MODE_CALLBACK, callback);
	}
	
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 组件内部私有方法
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private void addKey(String startKey, String endKey, int mode, Object ...others){
		assert (startKey != null && !startKey.equals(""));
		if(!keyCaseSensitive){
			startKey = startKey.toLowerCase();
			endKey = (endKey == null ? null : endKey.toLowerCase());
		}
		
		for(KeyExpression ke : keys){
			if(ke.getStartKey().equals(startKey))
				throw new IllegalArgumentException("设置的开始关键字重复, key = " + startKey);
		}
		
		KeyExpression ke = new KeyExpression(startKey, endKey);
		
		if(mode == RESULT_MODE_REPLACE || mode == RESULT_MODE_CALLBACK){
			if(others == null || others.length > 1)
				throw new IllegalArgumentException("argument is required in mode 'REPLACE' and 'CALLBACK'.");
			if(mode == RESULT_MODE_REPLACE){
				if(!(others[0] instanceof String))
					throw new IllegalArgumentException("argument of replace must be String.");
				else
					ke.setResultMode(RESULT_MODE_REPLACE)
					.setReplace(others[0].toString());
			}
			if(mode == RESULT_MODE_CALLBACK){
				if(!(others[0] instanceof CallBack))
					throw new IllegalArgumentException("argument of callback must be CallBack.");
				else
					ke.setResultMode(RESULT_MODE_CALLBACK)
					.setCallBack((CallBack)others[0]);
			}
		} else if(mode == RESULT_MODE_REMOVE){
			ke.setResultMode(RESULT_MODE_REMOVE);
		}
		keys.add(ke);
	}
	
	private void getMaxKeyLength(){
		int skSize = 0;
		int ekSize = 0;
		for(KeyExpression ke : keys){
			int k1 = ke.getStartKey().length();
			if(k1 > skSize){
				skSize = k1;
			}
			if(ke.getEndKey() != null && ke.getEndKey().length() > ekSize){
				ekSize = ke.getEndKey().length();
			}
		}
		this.maxStartKeyLength = skSize;
		this.maxEndKeyLength = ekSize;
		logger.debug("maxStartKeyLength = " + maxStartKeyLength);
		logger.debug("maxEndKeyLength = " + maxEndKeyLength);
	}
	
	private char previousRead;
	
	private void readOneCharacter(char c, boolean isLastRead){
		/* 读到空格，而且上一个也是空格，忽略 */
		if(c == 32 && previousRead == 32) return;
		
		checkKeyExcludedChar(c);
		
//		String scaned = getScanedChars().trim();
		String scaned = getScanedChars();
//		System.out.println("已扫描内容 = " + scaned);
		
		doMatchedInScaned(scaned);
		
		if(!isLastRead){
			scanStack.addLast(c);
			previousRead = c;
		} else {
			// 读到最后一个字符了，如果栈中存在内容就输出
			this.totalResult.append(getScanedChars());
		}
	}
	
	/**
	 * 栈中是否存在被排除的字符，如果存在返回<code> true </code>
	 * @return
	 */
	private boolean inExcludedCharStack(){
		return !keyExcludedCharStack.isEmpty();
	}
	
	/**
	 * 检查输入的字符是否是排除的字符
	 * @param c
	 */
	private void checkKeyExcludedChar(char c){
		if(keyExcludedCharSet.contains(c)){
			Character existInStack = this.keyExcludedCharStack.peekLast();
			if(existInStack != null && c == existInStack){
				logger.debug("读入的字符在排除字符栈中已经存在,配对成功, 可以清除了. char = " + c);
				keyExcludedCharStack.pollLast();
			} else {
				logger.debug("读入的字符是要排除的字符，但栈顶并没有匹配的，直接加入栈顶");
				keyExcludedCharStack.offerLast(c);
			}
		}
	}
	
	/**
	 * 在已经扫描的字符串中，是否存在匹配的关键词
	 * @return
	 */
	private boolean doMatchedInScaned(String scaned){
		String findStartKey = doMatchStartKey(scaned);
		if(findStartKey != null){
			/* 在清空扫描栈之前，需要保存到关键词对应变量中 */
			String savedData = null;
			String savedKey = null;
			if(!scaned.equals(findStartKey)) {
				savedData = this.contentIncludeKey ? scaned : scaned.replaceAll(findStartKey, "");
			}
			savedKey = existKeysStack.peekLast();
			if(savedKey != null){
				int index = storedKeyCounter.get(savedKey);
				List<StringBuilder> sbs = result2.get(new KeyExpression(savedKey
						, startEndKeys.get(savedKey)));
				sbs.get(index).append(savedData == null ? "" : savedData);
//				result.get(new KeyExpression(savedKey
//						, startEndKeys.get(savedKey))).append(savedData == null ? "" : savedData);
			}
			
			existKeysStack.offerLast(findStartKey);
			scanStack.clear();
			return true;
		}
		
		String existStartKey = existKeysStack.peekLast();
		String endKey = existStartKey == null ? null : startEndKeys.get(existStartKey);
		
		String matchedEndKey = doMatchEndKey(scaned, existStartKey, endKey);
		if(matchedEndKey != null){
			existKeysStack.pollLast();
			scanStack.clear();
			return true;
		}
		return false;
	}
	
	private String doMatchEndKey(String scaned, String startKey, String endKey){
		int _ss = scaned.length();
		int size = 0;
		String cbCall = null;
		for(KeyExpression ke : keys){
			if(ke.getEndKey() == null) continue;
			if(!ke.getStartKey().equals(startKey)) continue; // 必须是与开始匹配的结束，否则不比较
			size = ke.getEndKey().length();
//			if(_ss >= size && scaned.indexOf(ke.getEndKey()) >= 0){
			if(_ss >= size && scaned.endsWith(ke.getEndKey()) && !inExcludedCharStack()){
				if(ke.getResultMode() == RESULT_MODE_STORE){
					writeResult2(scaned, ke);
					
				} else if(ke.getResultMode() == RESULT_MODE_REMOVE){
					if(this.contentIncludeKey)
						totalResult.append(ke.getEndKey());
					
				} else if(ke.getResultMode() == RESULT_MODE_REPLACE){
					writeResult2(ke.getReplace(), ke);
					
				} else if(ke.getResultMode() == RESULT_MODE_CALLBACK){
					cbCall = ke.getCallBack().afterSegment(scaned, ke.getStartKey(), ke.getEndKey());
					writeResult2(cbCall, ke);
				}
				return ke.getEndKey();
			}
		}
		return null;
	}
	
	private Map<String, Integer> storedKeyCounter = new HashMap<String, Integer>(2);
	
	private String doMatchStartKey(String scaned){
		int _ss = scaned.length();
		int size = 0;
		String startKey = null;
		String previousKey = null; // 栈中保存的开始关键词
		for(KeyExpression ke : keys){
			startKey = ke.getStartKey();
			size = ke.getStartKey().length();
			if(_ss >= size && scaned.endsWith(startKey)){
				/* 如果后续字符串中包含了开始关键词，我们并不处理，因为主要通过单词来区分而不是字符 */
				previousKey = existKeysStack.peekLast();
				if(previousKey != null && previousKey.equals(startKey))
					continue;
				
				/* 删除关键词优先处理，如果发现栈里面已经存在关键词（开始） */
				/* 并且是一个删除类型，那么后面嵌套的关键词就不再处理。 */
				if(hasRemovedKeyInStack()) continue;
				
				/* 遇到内容中存在多处相同的关键词时，我们需要同时保留这些分析结果 */
				if(ke.getResultMode() != RESULT_MODE_REMOVE){
					Integer i = storedKeyCounter.get(startKey);
					if(i == null){
						i = 0;
						storedKeyCounter.put(startKey, i);
						logger.debug("已存储的key '" + startKey + "' i=0.");
					} else {
						storedKeyCounter.put(startKey, ++i);
						logger.debug("已存储的key '" + startKey + "' i=" + i);
					}
					List<StringBuilder> sbs = result2.get(new KeyExpression(startKey
							, startEndKeys.get(startKey)));
					sbs.add(i, new StringBuilder());
					logger.debug("key '" + startKey + "' 创建了第" + i + "个StringBuilder.");
				}
				/*--------------------------------------------------*/
				
				if(this.contentIncludeKey){
					totalResult.append(scaned);
				} else {
//					totalResult.append(scaned.replaceAll(ke.getStartKey(), ""));
					if(scaned.endsWith(ke.getStartKey())){
						int indx = scaned.length() - ke.getStartKey().length();
						totalResult.append(scaned.substring(0, indx));
					} else
						totalResult.append(scaned);
				}
				return ke.getStartKey();
			}
		}
		return null;
	}
	
	private void writeResult2(String scaned, KeyExpression ke){
		writeResult2(scaned, ke, true);
	}
	private void writeResult2(String scaned, KeyExpression ke, boolean includeTotalResult){
		int index = storedKeyCounter.get(ke.getStartKey());
		if(this.contentIncludeKey){
			result2.get(ke).get(index).append(scaned);
			if(includeTotalResult)
				totalResult.append(scaned);
		} else {
//			result2.get(ke).get(index).append(scaned.replaceAll(, ""));
//			totalResult.append(scaned.replaceAll(ke.getEndKey(), ""));
			int eIndx = scaned.length() - ke.getEndKey().length();
			result2.get(ke).get(index).append(scaned.substring(0, eIndx));
			if(includeTotalResult)
				totalResult.append(scaned.substring(0, eIndx));
		}
	}
	
	/**
	 * 在关键词栈中已经存在了"删除类型的关键词"
	 * @return 如果存在返回<code>true</code>
	 */
	private boolean hasRemovedKeyInStack(){
		String _existKey = existKeysStack.peekLast();
		if(_existKey == null) return false;
		KeyExpression _k = new KeyExpression(_existKey, startEndKeys.get(_existKey));
		for(KeyExpression ke : keys){
			if(ke.equals(_k) && ke.getResultMode() == RESULT_MODE_REMOVE){
				return true;
			}
		}
		return false;
	}
	
	private String getScanedChars(){
		StringBuilder sb = new StringBuilder(8);
		for(char c : scanStack){
			sb.append(c);
		}
		return sb.toString();
	}
	
	/**
	 * 定义关键词表达式对象，内部用来处理关键词的各种属性和逻辑</br>
	 * 这是封装的一个好处，系统通过此对象来完成对关键词的各种操作。
	 * @author shikeying
	 *
	 */
	private class KeyExpression {
		private String startKey;
		private String endKey;
		private int resultMode = RESULT_MODE_STORE;
		private CallBack callBack;
		private String replace;
		
		public KeyExpression(String startKey, String endKey){
			assert (startKey != null && !startKey.equals(""));
			if(!keyCaseSensitive){
				this.startKey = startKey.toLowerCase();
				this.endKey = (endKey == null ? null : endKey.toLowerCase());
			} else {
				this.startKey = startKey;
				this.endKey = endKey;
			}
		}
		
		public String getStartKey() {
			return startKey;
		}

		public String getEndKey() {
			return endKey;
		}

		public String getReplace() {
			return replace;
		}

		public CallBack getCallBack() {
			return callBack;
		}

		public int getResultMode() {
			return resultMode;
		}

		public KeyExpression setResultMode(int mode){
			this.resultMode = mode;
			return this;
		}
		
		public KeyExpression setReplace(String replace){
			this.replace = replace;
			return this;
		}
		public KeyExpression setCallBack(CallBack callback){
			this.callBack = callback;
			return this;
		}
		
		public int hashCode(){
			return (31 + 13*this.startKey.hashCode() 
					+ (this.endKey == null ? 0 : this.endKey.hashCode()*13));
		}
		
		public boolean equals(Object o){
			if(o == null) return false;
			if(o instanceof KeyExpression){
				KeyExpression ke = (KeyExpression)o;
				if(ke == this) return true;
				if(ke.startKey.equals(this.startKey)){
					return (ke.endKey == null ? 
							(this.endKey == null ? true : false) 
							: (this.endKey != null && this.endKey.equals(ke.endKey) ? true : false));
				}
			}
			return false;
		}
		
		public String toString(){
			return new StringBuilder().append("{skey=").append(startKey)
					.append(", ekey=").append(endKey)
					.append(", mode=").append(resultMode)
					.append(", replace=").append(replace)
					.append(", callback=").append(callBack==null ? "" : callBack.getClass().getName())
					.append("}").toString();
		}
	}
	
	/**
	 * 分段语句处理回调函数，用户可以自定义实现系统分析后的结果
	 * @author shikeying
	 *
	 */
	protected interface CallBack{
		String afterSegment(String segmentResult, String keyStart, String keyEnd);
	}
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * test method
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	void print(){
		System.out.println("previousRead = " + (int)previousRead);
		System.out.println("scanStack = " + scanStack.toArray());
		for(char c : scanStack)
			System.out.print(c);
	}
	
	public static void main(String[] args){
//		SegmentReader sr = new SegmentReader();
//		sr.addKey("boy", "girl");
//		sr.addKey("must ", null);
//		String result = sr.read("I am a boy, but my sister is     a good girl.");
//		sr.print();
//		System.out.println("========= result ======== \t");
//		System.out.println(result);
//		System.out.println("========= getKeys(boy,girl) ======== \t");
//		System.out.println(sr.getSolvedContent("boy", "girl"));
		
		StringBuilder test = new StringBuilder();
		test.append("OPEN out_cursor FOR\r\n");
		test.append("--查询电子钱包消费和月票消费\r\n");
		test.append("SELECT cardtype,\r\n");
		test.append("NVL (SUM (operno), 0) AS operno,\r\n");
		test.append("NVL (SUM (viceopermn), 0) AS viceopermn\r\n");
		test.append("to_char('sum',10,90) end");
		test.append("FROM (                              --电子钱包和月票钱包\r\n");
		test.append("select * from table t WHERE opdt >= TO_DATE(prmsdate, 'YYYY-MM-DD HH24:MI;SS'));");
		test.append("ELSE \r\n");
		test.append("OPEN out_cursor FOR querysql;\r\n");
		System.out.println(test);
		System.out.println("--------- start... ---------");
		
		SegmentReader sr2 = new SegmentReader();
		sr2.addKey("OPEN out_cursor FOR", ";");
		sr2.addRemoveKey("--", "\r\n");
		sr2.addKey("to_char", "end");
		System.out.println(sr2.read(test.toString()));
		System.out.println("------------------");
//		System.out.println(sr2.keys);
//		System.out.println(sr2.getSolvedContent("OPEN out_cursor FOR\r\n", ";"));
		System.out.println(sr2.getSolvedList("OPEN out_cursor FOR", ";"));
		System.out.println("##########");
		System.out.println(sr2.getSolvedContent("to_char", "end"));
	}
}
