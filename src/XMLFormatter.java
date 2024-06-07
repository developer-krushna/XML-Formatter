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

 

import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 Author @developer-krushna
 */
public class XMLFormatter {
	private FormattingOption formattingOption = FormattingOption.DEFAULT;
	private String indentationString = "    ";
	private final StringBuilder formattedXmlBuilder = new StringBuilder().append("");
	private boolean hasContent = false;
	
	public void setFormattingOption(int option) {
		switch (option) {
			case 1:
			this.formattingOption = FormattingOption.OPTION_A;
			return;
			case 2:
			this.formattingOption = FormattingOption.OPTION_B;
			return;
			case 3:
			this.formattingOption = FormattingOption.OPTION_C;
			return;
			default:
			this.formattingOption = FormattingOption.DEFAULT;
			//use default formattingOption
			return;
		}
	}
	
	public enum FormattingOption {
		OPTION_A,
		OPTION_B,
		OPTION_C,
		DEFAULT
	}

	/*
	 Author @developer-krushna
	 
	 Special thanks to Stack Overflow users amd ChatGPT(For code enhancement)
	 */
	public void setIndentation(int level) {
		this.indentationString = "";
		for (int i = 0; i < level; i++) {
			this.indentationString = new StringBuffer().append(this.indentationString).append(" ").toString();
		}
	}
	
	private void formatNode(Node node, String indentation) {
    if (node.getNodeType() == Node.ELEMENT_NODE) {
        String nodeName = node.getNodeName();
        NamedNodeMap attributes = node.getAttributes();
        NodeList childNodes = node.getChildNodes();

        if (childNodes.getLength() == 0) {
            // If no child nodes, format as self-closing tag
            this.formattedXmlBuilder.append("\n")
                    .append(indentation)
                    .append("<")
                    .append(nodeName)
                    .append(attributes == null ? "" : formatAttributes(attributes, indentation))
                    .append("/>");
        } else {
            // Format as open-close tag
            this.formattedXmlBuilder.append("\n")
                    .append(indentation)
                    .append("<")
                    .append(nodeName)
                    .append(attributes == null ? "" : formatAttributes(attributes, indentation))
                    .append(">");

            // Format child nodes
            for (int i = 0; i < childNodes.getLength(); i++) {
                formatNode(childNodes.item(i), indentation + this.indentationString);
            }

            // Close tag
            this.formattedXmlBuilder.append("\n")
                    .append(indentation)
                    .append("</")
                    .append(nodeName)
                    .append(">");
        }
    } else if (node.getNodeType() == Node.COMMENT_NODE) {
        // Format comment node
        this.formattedXmlBuilder.append("\n")
                .append(indentation)
                .append("<!--")
                .append(node.getTextContent())
                .append("-->");
    } else if (node.getNodeType() == Node.TEXT_NODE) {
        // Format text node
        String content = node.getTextContent().trim();
        if (!content.isEmpty()) {
            this.formattedXmlBuilder.append(content);
        }
    }
}
	
	private String formatAttributes(NamedNodeMap namedNodeMap, String indentation) {
		StringBuilder attributeStringBuilder = new StringBuilder().append("");
		int length = namedNodeMap.getLength();
		for (int i = 0; i < length; i++) {
			attributeStringBuilder.append("\n").append(indentation).append(this.indentationString);
			if (length == 1) {
				attributeStringBuilder.append(" ");
			}
			Node attributeNode = namedNodeMap.item(i);
			attributeStringBuilder.append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(attributeNode.getNodeName()).append("=\"").toString()).append(attributeNode.getNodeValue()).toString()).append("\"").toString());
		}
		return attributeStringBuilder.toString();
	}
	
	// format xml according to the node and indentation value
	private void formatXml(Node node, String indentation) {
    String nodeName = node.getNodeName();
    NamedNodeMap attributes = node.getAttributes();
    this.formattedXmlBuilder.append("\n")
            .append(indentation)
            .append("<")
            .append(nodeName)
            .append(attributes == null ? "" : formatAttributes(attributes, indentation));

    NodeList childNodes = node.getChildNodes();
    boolean hasElementChild = false;
    for (int i = 0; i < childNodes.getLength(); i++) {
        if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
            hasElementChild = true;
            break;
        }
    }

    if (hasElementChild) {
        this.formattedXmlBuilder.append(">");
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                formatXml(childNode, indentation + this.indentationString);
            }
        }
        this.formattedXmlBuilder.append("\n")
                .append(indentation)
                .append("</")
                .append(nodeName)
                .append(">");
    } else {
        // If no child element nodes, use self-closing tag
        this.formattedXmlBuilder.append("/>");
    }
}
	private String formatAttributesCompact(NamedNodeMap namedNodeMap, String indentation) {
		StringBuilder attributeStringBuilder = new StringBuilder().append("");
		int length = namedNodeMap.getLength();
		if (length == 0) {
			return "";
		}
		for (int i = 0; i < length; i++) {
			attributeStringBuilder.append("\n").append(this.indentationString);
			Node attributeNode = namedNodeMap.item(i);
			attributeStringBuilder.append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(attributeNode.getNodeName()).append("=\"").toString()).append(attributeNode.getNodeValue()).toString()).append("\"").toString());
		}
		return attributeStringBuilder.append("\n").toString();
	}
	
	private void formatXmlCompact(Node node, String indentation) {
		NodeList childNodes = node.getChildNodes();
		if(node.getNodeType() == 1) {
			String endTag = ">";
			if (childNodes.getLength() <= 0) {
				endTag = "/>";
			}
			String nodeName = node.getNodeName();
			NamedNodeMap attributes = node.getAttributes();
			this.formattedXmlBuilder.append("\n")
			.append(indentation)
			.append("<")
			.append(nodeName)
			.append(attributes == null ? "" : formatAttributesCompact(attributes, indentation))
			.append(endTag);
			NodeList childNodes2 = node.getChildNodes();
			int length = childNodes2.getLength();
			int i = 0;
			while (i < length) {
				formatXmlCompact(childNodes2.item(i), "");
				i++;
			}
			if (i != 0) {
				if (this.hasContent) {
					this.formattedXmlBuilder.append(new StringBuffer().append(new StringBuffer().append("</").append(nodeName).toString()).append(">").toString());
				} else {
					this.formattedXmlBuilder.append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append("\n").append("</").toString()).append(nodeName).toString()).append(">").toString());
				}
			}
			this.hasContent = false;
		} else if (node.getNodeType() == 8) {
			this.formattedXmlBuilder.append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append("\n").append(indentation).toString()).append("<!--").toString()).append(node.getTextContent()).toString()).append("-->").toString());
		} else if (node.getNodeType() == 3) {
			String content = node.getTextContent().trim();
			if (!content.isEmpty()) {
				if (this.formattedXmlBuilder.length() > 0) {
					int length2 = this.formattedXmlBuilder.length() - 1;
					if (this.formattedXmlBuilder.charAt(length2) == '\n') {
						this.formattedXmlBuilder.deleteCharAt(length2);
					}
				}
				this.formattedXmlBuilder.append(content);
				this.hasContent = true;
			}
		}
	}
	
	private String formatAttributesMinimized(NamedNodeMap namedNodeMap, String indentation) {
		StringBuilder attributeStringBuilder = new StringBuilder().append("");
		int length = namedNodeMap.getLength();
		for (int i = 0; i < length; i++) {
			Node attributeNode = namedNodeMap.item(i);
			attributeStringBuilder.append(" ");
			attributeStringBuilder.append(new StringBuffer().append(new StringBuffer().append(attributeNode.getNodeName()).append("=\"").toString()).append(attributeNode.getNodeValue()).toString());
		}
		return attributeStringBuilder.toString();
	}
	
	private void formatXmlMinimized(Node node, String indentation) {
		NodeList childNodes = node.getChildNodes();
		if (node.getNodeType() == 1) {
			String endTag = ">";
			if (childNodes.getLength() <= 0) {
				endTag = "/>";
			}
			String nodeName = node.getNodeName();
			NamedNodeMap attributes = node.getAttributes();
			this.formattedXmlBuilder.append("<")
			.append(nodeName)
			.append(attributes == null ? "" : formatAttributesMinimized(attributes, indentation))
			.append(endTag);
			NodeList childNodes2 = node.getChildNodes();
			int length = childNodes2.getLength();
			int i = 0;
			while (i < length) {
				formatXmlMinimized(childNodes2.item(i), "");
				i++;
			}
		} else if (node.getNodeType() == 8) {
			this.formattedXmlBuilder.append(new StringBuffer().append(new StringBuffer().append("<!--").toString()).append(node.getTextContent()).toString()).append("-->");
		} else if (node.getNodeType() == 3) {
			String content = node.getTextContent().trim();
			if (!content.isEmpty()) {
				this.formattedXmlBuilder.append(content);
			}
		}
	}
	
	private String formatAttributesForcedNewLines(NamedNodeMap namedNodeMap, String indentation) {
		StringBuilder attributeStringBuilder = new StringBuilder().append("");
		int length = namedNodeMap.getLength();
		for (int i = 0; i < length; i++) {
			Node attributeNode = namedNodeMap.item(i);
			attributeStringBuilder.append("\n").append(this.indentationString);
			attributeStringBuilder.append(new StringBuffer().append(new StringBuffer().append(attributeNode.getNodeName()).append("=\"").toString()).append(attributeNode.getNodeValue()).toString());
		}
		return attributeStringBuilder.append("\n").toString();
	}
	
	private void formatXmlForcedNewLines(Node node, String indentation) {
		NodeList childNodes = node.getChildNodes();
		if (node.getNodeType() == 1) {
			String endTag = ">\n";
			if (childNodes.getLength() <= 0) {
				endTag = "/>\n";
			}
			String nodeName = node.getNodeName();
			NamedNodeMap attributes = node.getAttributes();
			this.formattedXmlBuilder.append("\n")
			.append("<")
			.append(nodeName)
			.append(attributes == null ? "" : formatAttributesForcedNewLines(attributes, indentation))
			.append(endTag);
			NodeList childNodes2 = node.getChildNodes();
			int length = childNodes2.getLength();
			int i = 0;
			while (i < length) {
				formatXmlForcedNewLines(childNodes2.item(i), "");
				i++;
			}
		} else if (node.getNodeType() == 8) {
			this.formattedXmlBuilder.append(new StringBuffer().append(new StringBuffer().append("\n").append("<!--").toString()).append(node.getTextContent()).toString()).append("-->\n");
		} else if (node.getNodeType() == 3) {
			String content = node.getTextContent().trim();
			if (!content.isEmpty()) {
				this.formattedXmlBuilder.append(content);
			}
		}
	}
	
	public String format(String xmlContent) {
		NodeList childNodes;
		int i = 0;
		if (!isValidXml(xmlContent)) {
			return null;
		}
		String formattedXml = null;
		this.hasContent = false;
		this.formattedXmlBuilder.replace(0, this.formattedXmlBuilder.length(), "");
		try {
			xmlContent.trim();
			if (xmlContent.startsWith("<?")) {
				int length = xmlContent.length();
				for (int i2 = 0; i2 < length; i2++) {
					char charAt = xmlContent.charAt(i2);
					this.formattedXmlBuilder.append(charAt);
					if (charAt == '>') {
						break;
					}
				}
			}
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlContent.getBytes());
			Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(byteArrayInputStream);
			if (parse == null) {
				this.formattedXmlBuilder.replace(0, this.formattedXmlBuilder.length(), "");
				return null;
			}
			try {
				byteArrayInputStream.close();
			} catch (Exception e2) {
			}
			parse.normalize();
			FormattingOption selectedFormattingOption = this.formattingOption;
			if (selectedFormattingOption == FormattingOption.OPTION_A) {
				NodeList childNodes2 = parse.getChildNodes();
				if (childNodes2 != null) {
					int length2 = childNodes2.getLength();
					while (i < length2) {
						formatXmlForcedNewLines(childNodes2.item(i), "");
						i++;
					}
				}
			} else if (selectedFormattingOption == FormattingOption.OPTION_B) {
				NodeList childNodes3 = parse.getChildNodes();
				if (childNodes3 != null) {
					int length3 = childNodes3.getLength();
					while (i < length3) {
						formatXmlCompact(childNodes3.item(i), "");
						i++;
					}
				}
			} else if (selectedFormattingOption == FormattingOption.OPTION_C) {
				NodeList childNodes4 = parse.getChildNodes();
				if (childNodes4 != null) {
					int length4 = childNodes4.getLength();
					while (i < length4) {
						formatXmlMinimized(childNodes4.item(i), "");
						i++;
					}
				}
				//Recommended this only
			} else if (selectedFormattingOption == FormattingOption.DEFAULT && (childNodes = parse.getChildNodes()) != null) {
				int length5 = childNodes.getLength();
				while (i < length5) {
					formatNode(childNodes.item(i), "");
					i++;
				}
			}
			String trimmedFormattedXml = this.formattedXmlBuilder.toString().trim();
			this.formattedXmlBuilder.replace(0, this.formattedXmlBuilder.length(), "");
			return trimmedFormattedXml;
		} catch (Exception e) {
			try {
				this.formattedXmlBuilder.replace(0, this.formattedXmlBuilder.length(), "");
				return formattedXml;
			} catch (Exception e4) {
				return formattedXml;
			}
		}
	}
	// check the xml text is valid or not
	public boolean isValidXml(String xmlContent) {
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlContent.getBytes());
			Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(byteArrayInputStream);
			if (parse == null) {
				return false;
			}
			try {
				byteArrayInputStream.close();
			} catch (Exception e2) {
			}
			parse.normalize();
			return true;
		} catch (Exception e3) {
			return false;
		}
	}
	
	protected void finalize() throws Throwable {
		try {
			this.hasContent = false;
			this.formattedXmlBuilder.replace(0, this.formattedXmlBuilder.length(), "");
		} catch (Exception e) {
			// no need
		}
		super.finalize();
	}
}
