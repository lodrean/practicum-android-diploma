package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyFullDto(
    @SerializedName("accept_handicapped")
    val acceptHandicapped: Boolean,
    @SerializedName("accept_incomplete_resumes")
    val acceptIncompleteResumes: Boolean,
    @SerializedName("accept_kids")
    val acceptKids: Boolean,
    @SerializedName("accept_temporary")
    val acceptTemporary: Boolean,
    val address: AddressDto,
    @SerializedName("allow_messages")
    val allowMessages: Boolean,
    @SerializedName("alternate_url")
    val alternateUrl: String,
    @SerializedName("apply_alternate_url")
    val applyAlternateUrl: String,
    val approved: Boolean,
    val archived: Boolean,
    val area: AreaDto,
    @SerializedName("billing_type")
    val billingType: BillingTypeDto,
    // val branded_description: Any,
    // val code: Any,
    val contacts: ContactsDto,
    @SerializedName("created_at")
    val createdAt: String,
    val department: DepartmentDto,
    val description: String,
    // val driver_license_types: List<Any>,
    val employer: EmployerDto,
    val employment: EmploymentDto,
    val experience: ExperienceDto,
    @SerializedName("has_test")
    val hasTest: Boolean,
    val hidden: Boolean,
    val id: String,
    @SerializedName("initial_created_at")
    val initialCreatedAt: String,
    // val insider_interview: Any,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDto>,
    // val languages: List<Any>,
    val name: String,
    // val negotiations_url: Any,
    val premium: Boolean,
    @SerializedName("professional_roles")
    val professionalRoles: List<ProfessionalRoleDto>,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("quick_responses_allowed")
    val quickResponsesAllowed: Boolean,
    // val relations: List<Any>,
    @SerializedName("response_letter_required")
    val responseLetterRequired: Boolean,
    // val response_url: Any,
    val salary: SalaryDto,
    val schedule: ScheduleDto,
    // val specializations: List<Any>,
    // val suitable_resumes_url: Any,
    // val test: Any,
    val type: TypeDto,
    // val vacancy_constructor_template: Any,
    // val working_days: List<Any>,
    @SerializedName("working_time_intervals")
    val workingTimeIntervals: List<WorkingTimeIntervalDto>,
    @SerializedName("working_time_modes")
    val workingTimeModes: List<WorkingTimeModeDto>
)
