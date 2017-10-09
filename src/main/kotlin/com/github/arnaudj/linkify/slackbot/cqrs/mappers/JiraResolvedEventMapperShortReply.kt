package com.github.arnaudj.linkify.slackbot.cqrs.mappers

import com.github.arnaudj.linkify.cqrs.ReplyEventMapper
import com.github.arnaudj.linkify.slackbot.cqrs.events.JiraResolved
import com.github.arnaudj.linkify.spi.jira.JiraEntity
import com.ullink.slack.simpleslackapi.SlackPreparedMessage

class JiraResolvedEventMapperShortReply : JiraResolvedEventMapperBase(), ReplyEventMapper<JiraResolved, List<SlackPreparedMessage>> {
    override fun mapEntity(jiraHostURL: String, e: JiraEntity): SlackPreparedMessage {
        return SlackPreparedMessage.Builder().withMessage(formatJiraIssueLinkAndSummary(jiraHostURL, e)).build()
    }

    private fun formatJiraIssueLinkAndSummary(jiraHostURL: String, e: JiraEntity) =
            formatLink(jiraHostURL, e) + backtickWrapper(getTitle(e, ""))

    private fun formatLink(jiraHostURL: String, e: JiraEntity) = "<${getIssueHref(jiraHostURL, e)}|${e.key}>"

    private fun backtickWrapper(s: String) = if (s.isNotEmpty()) " `$s`" else ""
}
