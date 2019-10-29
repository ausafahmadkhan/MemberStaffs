package com.example.Member.Client.StudyMaterialClient;

import com.example.Material.MaterialResponses.TopicResponse;
import com.example.Member.MemberResponse.ResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StudyMaterialClient
{
    @GET("/StudyMaterials/getTopic/{topicId}")
    Call<ResponseModel<TopicResponse>> getTopic(@Path("topicId") String topicId);

}
