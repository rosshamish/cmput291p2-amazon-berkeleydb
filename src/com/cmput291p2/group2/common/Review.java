package com.cmput291p2.group2.common;

import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class Review models a product review. It can be built by supplying either:
 *
 * <ul>
 *     <li>a list of strings in the format expected in stdin by Phase 1's
 *         {@link com.cmput291p2.group2.Phase1.ReviewFileWriter}. This guarantees
 *         unique review ids during the lifetime of the program.
 *     </li>
 *     <li>
 *         a list of strings in the format expected as input by Phase 2's
 *         {@link com.cmput291p2.group2.Phase2.IndexBuilder}. This is the same
 *         format expected to be outputted by Phase 1's
 *         {@link com.cmput291p2.group2.Phase1.ReviewFileWriter}.
 *     </li>
 *     <li>
 *         all required attributes, including the review id
 *     </li>
 * </ul>
 */
public class Review {
    private static Integer nextReviewId = 1;
    private Integer getNextReviewId() {
        return nextReviewId++;
    }

    private static Pattern attributeNamePattern = Pattern.compile("\\w+/(\\w+): (.*)");

    private Integer reviewId;

    private String productId;
    private String title;
    private String price;
    private String userId;
    private String profileName;
    private String helpfulness;
    private String score;
    private String time;
    private String summary;
    private String text;

    /**
     * Creates a Review object given a list of strings as specified in the eClass
     * specification and eClass example input files. These strings must be of the
     * format expected by Phase 1's {@link com.cmput291p2.group2.Phase1.ReviewFileWriter}.
     *
     * Each string in the list is one line in the input file.
     * All attributes are expected as defined in the switch-case statement in
     * {@link Review#setAttribute(String)}
     *
     * This constructor should be used by {@link com.cmput291p2.group2.Phase1.ReviewFileWriter}.
     */
    public Review(Collection<String> phase1InputFileLines) {
        this.reviewId = getNextReviewId();

        try {
            for (String inputFileLine : phase1InputFileLines) {
                this.setAttribute(inputFileLine);
            }
        } catch (UnknownAttributeException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Creates a Review object given the comma separated list of attributes as
     * specified in the eClass specification and eClass example input files. The
     * string must be of the format expected by Phase 2's
     * {@link com.cmput291p2.group2.Phase2.IndexBuilder}.
     *
     * This comma separated string will be the output of
     * {@link com.cmput291p2.group2.Phase1.ReviewFileWriter}.
     *
     * This constructor should be used by {@link com.cmput291p2.group2.Phase2.IndexBuilder}.
     */
    public Review(String commaSeparatedAttributes) {

    }

    /**
     * Creates a Review object given all its properties.
     */
    public Review(Integer reviewId,
                  String productId, String title, String price,
                  String userId, String profileName,
                  String helpfulness, String score, String time,
                  String summary, String text) {
        this.reviewId = reviewId;

        this.productId = productId;
        this.title = title;
        this.price = price;
        this.userId = userId;
        this.profileName = profileName;
        this.helpfulness = helpfulness;
        this.score = score;
        this.time = time;
        this.summary = summary;
        this.text = text;
    }

    private void setAttribute(String inputFileLine) throws UnknownAttributeException {
        Matcher m = attributeNamePattern.matcher(inputFileLine);
        m.find();
        String attributeName = m.group(1);
        String attributeValue = m.group(2);
        switch(attributeName) {
            case "productId":
                this.productId = attributeValue;
                break;
            case "title":
                this.title = attributeValue;
                break;
            case "price":
                this.price = attributeValue;
                break;
            case "userId":
                this.userId = attributeValue;
                break;
            case "profileName":
                this.profileName = attributeValue;
                break;
            case "helpfulness":
                this.helpfulness = attributeValue;
                break;
            case "score":
                this.score = attributeValue;
                break;
            case "time":
                this.time = attributeValue;
                break;
            case "summary":
                this.summary = attributeValue;
                break;
            case "text":
                this.text = attributeValue;
                break;
            default:
                throw new UnknownAttributeException(String.format("Unknown attribute %s found in input", attributeName));
        }
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public String getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getUserId() {
        return userId;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getHelpfulness() {
        return helpfulness;
    }

    public String getScore() {
        return score;
    }

    public Date getTime() {
        return new Date(Long.valueOf(time));
    }

    public String getSummary() {
        return summary;
    }

    public String getText() {
        return text;
    }

}
