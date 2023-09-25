package api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResumeServiceTest {

  private ResumeService resumeService;

  @BeforeEach
  void setUp() {
    this.resumeService = new DefaultResumeService();
  }

  @Test
  void testGetResumeData() throws Exception {
    String expectedData =
        "{\"basics\":{\"name\":\"Your first and last name\",\"label\":\"\",\"picture\":\"\",\"email\":\"Your email address\",\"phone\":\"A phone number, with any formatting you like. E.g. (555) 555-5555.\",\"degree\":\"\",\"website\":\"Your website URL\",\"summary\":\"A one-sentence to one-paragraph overview text. Do not include any line-breaks.\",\"location\":{\"address\":\"Your street address or mailing address\",\"postalCode\":\"Your postal code (ZIP in the U.S.)\",\"city\":\"Your city\",\"countryCode\":\"Your country (e.g. USA)\",\"region\":\"Your region (state in the U.S.)\"},\"profiles\":[{\"network\":\"A social media or other profile that you would like to include (e.g. LinkedIn, Twitter)\",\"username\":\"Your username on this network\",\"url\":\"A URL to your user profile page\"}]},\"work\":[{\"company\":\"Your employer name\",\"position\":\"Your job title\",\"website\":\"The URL for the employer's website\",\"startDate\":\"Your start date, in YYYY-MM-DD format\",\"endDate\":\"Your end date, in YYY-MM-DD format (leave blank for a current position)\",\"summary\":\"A one-sentence to one-paragraph summary of this employer or position\",\"highlights\":[\"Bullet-point list items that you would like to include along with (or instead of) a summary paragraph.\"]}],\"volunteer\":[{\"organization\":\"Your volunteer organization name\",\"position\":\"Your job title\",\"website\":\"The URL for the employer's website\",\"startDate\":\"Your start date, in YYYY-MM-DD format\",\"endDate\":\"Your end date, in YYY-MM-DD format (leave blank for a current position)\",\"summary\":\"A one-sentence to one-paragraph summary of this employer or position\",\"highlights\":[\"Bullet-point list items that you would like to include along with (or instead of) a summary paragraph.\"]}],\"education\":[{\"institution\":\"Your school name\",\"area\":\"Your area of study or degree earned\",\"studyType\":\"\",\"startDate\":\"Your start date, in YYYY-MM-DD format\",\"endDate\":\"Your completion date, in YYYY-MM-DD format\",\"gpa\":\"\",\"courses\":[\"\"]}],\"awards\":[{\"title\":\"Your award title\",\"date\":\"Your date, in YYYY-MM-DD format you received the award\",\"awarder\":\"Your award given by\",\"summary\":\"A one-sentence to one-paragraph overview of this award\"}],\"publications\":[{\"name\":\"Your publication title\",\"publisher\":\"Publisher name\",\"releaseDate\":\"Publication date, in YYYY-MM-DD format\",\"website\":\"The website URL for this publisher or book\",\"summary\":\"A one-sentence to one-paragraph overview of this publication\"}],\"skills\":[{\"name\":\"A category of job skills (e.g. 'Programming Languages')\",\"level\":\"\",\"keywords\":[\"Keywords under this category (e.g. 'Java', 'C++', etc)\"]}],\"languages\":[{\"language\":\"Language name\",\"fluency\":\"Your language fluency\"}],\"interests\":[{\"name\":\"A category of interests (e.g. 'Sports')\",\"keywords\":[\"Keywords under this category (e.g. 'Cricket', 'Football', 'Golf')\"]}]}";
    String actualData = resumeService.getResumeData();

    ObjectMapper mapper = new ObjectMapper();

    String expectedDataCompacted = mapper.writeValueAsString(mapper.readTree(expectedData));
    String actualDataCompacted = mapper.writeValueAsString(mapper.readTree(actualData));

    assertEquals(expectedDataCompacted, actualDataCompacted);
  }
}
